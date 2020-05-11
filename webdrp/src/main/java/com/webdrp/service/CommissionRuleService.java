package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.common.Pager;
import com.webdrp.entity.CommissionRule;
import com.webdrp.entity.vo.CommissionRuleVo;

import java.util.List;

public interface CommissionRuleService extends BaseService<CommissionRule> {

    public void insert(CommissionRule commissionRule);

    void insertByModeId(Integer  modeId);

    void insertByGradeRank(Integer gradeRank);

    void deleteByGradeRank(Integer grandRank);

    List<CommissionRuleVo> loadAll(CommissionRuleVo commissionRule, Pager pager);
}
