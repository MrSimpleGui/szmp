package com.webdrp.service.impl;

import com.webdrp.common.BaseServiceImpl;
import com.webdrp.common.Pager;
import com.webdrp.entity.CommissionMode;
import com.webdrp.entity.Grade;
import com.webdrp.entity.Income;
import com.webdrp.entity.vo.GradeVo;
import com.webdrp.err.BusinessException;
import com.webdrp.mapper.CommissionModeMapper;
import com.webdrp.mapper.GradeMapper;
import com.webdrp.service.CommissionRuleService;
import com.webdrp.service.GradeService;
import com.webdrp.util.DateUtils;
import com.webdrp.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by yuanming on 2018/8/22.
 */
@Service
public class GradeServiceImpl extends BaseServiceImpl<Grade, GradeMapper> implements GradeService{

    @Autowired
    GradeMapper gradeMapper;

    @Autowired
    private CommissionRuleService commissionRuleService;

    @Autowired
    private CommissionModeMapper commissionModeMapper;


    @Override
    public void insert(Grade grade) {
        try {
            //插入之前判断模式字段是否合法
            isEmpty(grade);

            grade.beforeCreate();
            gradeMapper.insert(grade);
            //插入角色之后在规则表插入规则
            commissionRuleService.insertByGradeRank(grade.getRank());
        } catch (NumberFormatException e){
            throw new BusinessException("指定模式字段包含非法字符");
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("添加分佣模式失败");
        }
    }

    public void update(Grade grade){
        try {
            //插入之前判断模式字段是否合法
            isEmpty(grade);
            super.update(grade);
        } catch (NumberFormatException e){
            throw new BusinessException("指定模式字段包含非法字符");
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("更新分佣模式失败");
        }
    }


    public Grade findByRank(Integer rank) {
        return gradeMapper.findByRank(rank);
    }

    @Override
    public void delete(Grade grade) {
        try {
            //查看该角色是否已被删除
            grade = gradeMapper.findById(grade.getId());

            // 先删除所有角色相关规则
            commissionRuleService.deleteByGradeRank(grade.getRank());
            grade.beforeDelete();
            gradeMapper.delete(grade);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("删除用户角色失败");
        }
    }

    @Override
    public Boolean findGradeByNameAndRank(Grade grade) {
        try {
            List<Grade> gradeList = gradeMapper.findGradeByNameAndRank(grade) ;
            if(gradeList.size() != 0){
                return true;
            }
            return false;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("添加分佣模式失败");
        }

    }

    @Override
    public List<GradeVo> loadAllVo(Grade grade, Pager pager) {
        try {
            List<Grade> gradeList = loadAll(grade, pager);
            List<GradeVo> gradeVoList = new ArrayList<>();
            gradeList.forEach(item->{
                GradeVo gradeVo = new GradeVo();
                BeanUtils.copyProperties(item, gradeVo);
                if(Objects.nonNull(item.getSpecificMode()) || !item.getSpecificMode().equals("0")){
                    List<Integer> modeIds = StringUtils.strToInteger(item.getSpecificMode());
                    if(modeIds.size() != 0){
                        List<CommissionMode> modeList = commissionModeMapper.findByIds(modeIds);
                        gradeVo.setModeList(modeList);
                    }
                }
                gradeVoList.add(gradeVo);
            });
            return gradeVoList;
        } catch (NumberFormatException e){
            throw new BusinessException("指定模式字段包含非法字符");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("指定模式字段包含非法字符");
        }
    }

    private void isEmpty(Grade grade){
        if(Objects.nonNull(grade.getSpecificMode()) && !grade.getSpecificMode().equals("0")){
            List<Integer> list = StringUtils.strToInteger(grade.getSpecificMode());
            if(list.size() == 0){
                throw new BusinessException("非法字符");
            }
        }
    }
}
