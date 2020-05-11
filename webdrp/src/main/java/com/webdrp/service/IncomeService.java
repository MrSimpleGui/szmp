package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.common.Pager;
import com.webdrp.entity.Income;
import com.webdrp.entity.Order;
import com.webdrp.entity.Member;
import com.webdrp.entity.vo.IncomeNameVo;
import com.webdrp.entity.vo.IncomeVo;

import java.util.List;

/**
 * Created by yuanming on 2018/8/13.
 */
public interface IncomeService  extends BaseService<Income> {

    Double sumIncome();

    /**
     * 发放佣金
     * @param order
     * @param member
     * @param grade
     * @param recordType
     */
    void addIncomeToUser(Order order, Member member, Integer floor, Integer grade, Integer recordType);

    /**
     * 发放佣金
     * @param member 目标用户
     * @param income 收入
     */
    void addIncomeToUser(Member member, Income income);

    /**
     * 级差版本 TODO 需不需要查询一遍再给
     * @param member
     * @param income
     */
    void addIncomeToUserV1(Member member, Income income);

    void addIncomeJDToUser(Order order, Member member, Integer floor, Integer grade, Integer recordType);

    Double sumUserIncome(Integer id);

    /**
     * 查找直推单数
     * @param richUserId
     * @return
     */
    int findZhitui(Integer richUserId);

    /**
     * 团队业绩
     * @param targetUserId
     * @return
     */
    int sumTeam(Integer targetUserId);

    /**
     * 删除用户佣金
     * @param income
     */
    void deleteUserIncome(Income income);

    List<IncomeVo> findMyIncome(Income income, Pager pager);

    long countMyIncome(Income income);

    double sumDayIncome(Income income);
    //直推张数，极差专用
    int findJichaZhiTui(Member member);
//    团队张数，极差专用，有重复
    int findJiChaTuanDui(Member member);
    // 团队重复的
    int findJiChaOthers(Member member);

    /**
     * 直推
     * @param member
     * @return
     */
    // 一级订单
    int findOneFloorOrder(Member member);
    // 二级订单
    int secondFloorOrder(Member member);
    // 团队订单
    int teamOrder(Member member);

    List<IncomeNameVo> loadAll(IncomeNameVo incomeNameVo, Pager pager);


}
