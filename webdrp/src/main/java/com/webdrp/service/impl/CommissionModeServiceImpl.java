package com.webdrp.service.impl;

import com.webdrp.common.BaseServiceImpl;
import com.webdrp.entity.CommissionMode;
import com.webdrp.entity.CommissionRule;
import com.webdrp.err.BusinessException;
import com.webdrp.mapper.CommissionModeMapper;
import com.webdrp.mapper.CommissionRuleMapper;
import com.webdrp.service.CommissionModeService;
import com.webdrp.service.CommissionRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommissionModeServiceImpl extends BaseServiceImpl<CommissionMode, CommissionModeMapper> implements CommissionModeService {

    @Autowired
    private CommissionModeMapper commissionModeMapper;

    @Autowired
    private CommissionRuleMapper commissionRuleMapper;

    @Lazy
    @Autowired
    private CommissionRuleService commissionRuleService;

    @Override
    public void insert(CommissionMode commissionMode) {
        try {
            commissionMode.beforeCreate();
            commissionModeMapper.insert(commissionMode);
            //获取当前所有角色，为每个角色生成一条新的规则
            if(commissionMode.getId() != null){
                commissionRuleService.insertByModeId(commissionMode.getId());
            }
        } catch (BusinessException b) {
            throw b;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("添加分佣模式失败");
        }
    }

    public void delete(CommissionMode commissionMode) {
        try {
            //先删除关联的规则
            CommissionRule commissionRule = new CommissionRule();
            commissionRule.setModeId(commissionMode.getId());
            commissionRule.beforeDelete();
            commissionRuleMapper.deleteByModeId(commissionRule);
            //删除该模板
            commissionMode.beforeDelete();
            commissionModeMapper.delete(commissionMode);
        } catch (BusinessException b) {
            throw b;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("添加分佣模式失败");
        }
    }

    @Override
    public Boolean selectModeByName(String name) {
        try {
            //查看该模式名是否已存在
            CommissionMode selectMode = new CommissionMode();
            selectMode.setName(name);
            List<CommissionMode> modeList = commissionModeMapper.findAll(selectMode);
            if(modeList.size() != 0){
                return true;
            }
            return false;
        } catch (BusinessException b) {
            throw b;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("添加分佣模式失败");
        }
    }


}
