package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.common.Pager;
import com.webdrp.entity.CommissionRule;
import com.webdrp.entity.vo.CommissionRuleVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CommissionRuleMapper extends BaseMapper<CommissionRule> {

    List<CommissionRule> findRuleByModeIds(@Param("modeIdList") List<Integer> modeIdList);

    void deleteByModeId(CommissionRule commissionRule);

    void deleteByGradeRank(CommissionRule commissionRule);

    List<CommissionRuleVo> loadAll(@Param(value = "entity")CommissionRuleVo commissionRule, @Param(value = "pager") Pager pager);
}
