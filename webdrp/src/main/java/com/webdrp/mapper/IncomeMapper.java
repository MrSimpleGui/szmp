package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.common.Pager;
import com.webdrp.entity.Income;
import com.webdrp.entity.vo.IncomeNameVo;
import com.webdrp.entity.vo.IncomeVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yuanming on 2018/8/13.
 */
@Component
public interface IncomeMapper extends BaseMapper<Income>{

    Double sumIncome();

    Double sumPriceByRichUserId(Integer richuserId);

    int sumZhiTui(Integer targetUserId);

    int sumTeam(Integer targetUserId);

    List<IncomeVo> findMyIncome(@Param(value = "entity") Income income,@Param(value = "pager") Pager pager);

    Income findIncome(Income income);

    /**
     * 防止重复
     * @param income
     * @return
     */
    Income findIncomeByOrderIdAndTargetIdAndIncomeTypeAndFloor(Income income);

    Income findIncomeByOrderIdAndTargetIdAndIncomeType(Income income);

    int sumIncomeByOrderId(Integer orderId);

    Integer findCanJjanDian(Income income);

    Integer findTongJi(Income income);
    //直推数量
    int findJichaZhiTui(Integer richUserId);
    //团队数量
    int findJiChaTuanDui(Integer richUserId);

    double sumDayIncome(Income income);

    Integer sumOthers(Integer targetUserId);

    //查看订单有没有返佣金
    int findCountByOrderId(Income income);

    long countMyIncome(Income income);

    /**
     * 一级
     * @param id
     * @return
     */
    int findOneFloorOrder(Integer id);

    /**
     * 二级
     * @param id
     * @return
     */
    int secondFloorOrder(Integer id);

    /**
     * 三级
     * @param id
     * @return
     */
    int teamOrder(Integer id);

    List<IncomeNameVo> findVoByPage(@Param("entity") IncomeNameVo incomeNameVo, @Param("pager") Pager pager);

    Double sumIncomeByMemberId(Integer memberId);

}
