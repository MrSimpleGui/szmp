package com.webdrp.service.impl;

import com.webdrp.common.BaseServiceImpl;
import com.webdrp.common.Pager;
import com.webdrp.entity.CommissionMode;
import com.webdrp.entity.CommissionRule;
import com.webdrp.entity.Grade;
import com.webdrp.entity.vo.CommissionRuleVo;
import com.webdrp.err.BusinessException;
import com.webdrp.mapper.CommissionModeMapper;
import com.webdrp.mapper.CommissionRuleMapper;
import com.webdrp.mapper.GradeMapper;
import com.webdrp.service.CommissionRuleService;
import com.webdrp.util.DoubleUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CommissionRuleServiceImpl extends BaseServiceImpl<CommissionRule, CommissionRuleMapper> implements CommissionRuleService {

    @Autowired
    private CommissionRuleMapper commissionRuleMapper;

    @Autowired
    private CommissionModeMapper commissionModeMapper;

    @Lazy
    @Autowired
    private GradeMapper gradeMapper;

    @Override
    public void insert(CommissionRule commissionRule) {
        try {
            commissionRule.beforeCreate();
            if(Objects.isNull(commissionRule.getGradeRank())){
                commissionRule.setGradeRank(0);
            }
            if(Objects.isNull(commissionRule.getModeId())){
                commissionRule.setModeId(0);
            }
            if(Objects.isNull(commissionRule.getType())){
                commissionRule.setType(0);
            }
            if(Objects.isNull(commissionRule.getFirstLevel())){
                commissionRule.setFirstLevel(0d);
            }
            if(Objects.isNull(commissionRule.getSecondLevel())){
                commissionRule.setSecondLevel(0d);
            }
            if(Objects.isNull(commissionRule.getTeamReward())){
                commissionRule.setTeamReward(0d);
            }
            if(Objects.isNull(commissionRule.getSidewayCommission())){
                commissionRule.setSidewayCommission(0d);
            }
            if(Objects.isNull(commissionRule.getSidewayNumber())){
                commissionRule.setSidewayNumber(0);
            }
            //判断分佣比例是否超过90%
            if(commissionRule.getType() == 0){
                if(!calProp(commissionRule)){
                    throw new BusinessException("分佣总比例大于0.9，请重新设置！");
                }
            }
            commissionRuleMapper.insert(commissionRule);
        } catch (BusinessException b) {
            throw b;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("添加分佣模式失败");
        }
    }

    @Override
    public void insertByModeId(Integer modeId) {
        try {
            CommissionRule modeRule = new CommissionRule();
            modeRule.setModeId(modeId);
            //判断该modeId是否已生成过规则
            List<CommissionRule> ruleList = commissionRuleMapper.findAll(modeRule);
            if(ruleList.size() != 0){
                log.debug("该模式id已生成过分佣规则");
                return;
            }
            List<Grade> gradeList = gradeMapper.findAll(new Grade());
            if(gradeList.size() == 0){
                return;
            }
            gradeList.forEach(item->{
                if(item.getRank() != 0){
                    CommissionRule commissionRule = new CommissionRule();
                    commissionRule.setModeId(modeId);
                    commissionRule.setGradeRank(item.getRank());
                    insert(commissionRule);
                }
            });
        } catch (BusinessException b) {
            throw b;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("添加分佣模式失败");
        }

    }

    @Override
    public void insertByGradeRank(Integer gradeRank) {
        try {
            List<Integer> modeIdList = commissionModeMapper.findModeIdList();
            if(modeIdList.size() == 0){
                return;
            }
            if(gradeRank != 0){
                modeIdList.forEach(item->{
                    CommissionRule commissionRule = new CommissionRule();
                    commissionRule.setModeId(item);
                    commissionRule.setGradeRank(gradeRank);
                    insert(commissionRule);
                });
            }
        } catch (BusinessException b) {
            throw b;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("添加分佣模式失败");
        }
    }


    @Override
    public void deleteByGradeRank(Integer grandRank) {
        try {
            CommissionRule commissionRule = new CommissionRule();
            commissionRule.setGradeRank(grandRank);
            commissionRule.beforeDelete();
            commissionRuleMapper.deleteByGradeRank(commissionRule);
        } catch (BusinessException b) {
            throw b;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("删除分佣规则失败");
        }
    }

    @Override
    public List<CommissionRuleVo> loadAll(CommissionRuleVo commissionRule, Pager pager) {
        long countAll = commissionRuleMapper.count(commissionRule);
        pager = getPager(countAll, pager);
        return commissionRuleMapper.loadAll(commissionRule, pager);
    }

    private Boolean calProp(CommissionRule commissionRule){
        Double amount = 0d;
        Double standard = 0.9d;
        DoubleUtil.add(amount,
                DoubleUtil.add(
                        DoubleUtil.add(
                                DoubleUtil.add(commissionRule.getTeamReward(),
                                        commissionRule.getSidewayCommission()),
                                commissionRule.getSecondLevel()
                        ),
                        commissionRule.getFirstLevel()
                )
        );
        if(amount > standard){
            return false;
        }
        return true;
    }


}
