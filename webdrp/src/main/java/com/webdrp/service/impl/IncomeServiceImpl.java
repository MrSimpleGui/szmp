package com.webdrp.service.impl;

import com.webdrp.common.BaseServiceImpl;
import com.webdrp.common.Pager;
import com.webdrp.constant.CommodityType;
import com.webdrp.constant.IncomeType;
import com.webdrp.entity.Income;
import com.webdrp.entity.Order;
import com.webdrp.entity.Member;
import com.webdrp.entity.vo.IncomeNameVo;
import com.webdrp.entity.vo.IncomeVo;
import com.webdrp.mapper.IncomeMapper;
import com.webdrp.mapper.MemberMapper;
import com.webdrp.service.IncomeService;
import com.webdrp.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by yuanming on 2018/8/13.
 */
@Service
public class IncomeServiceImpl extends BaseServiceImpl<Income,IncomeMapper>  implements IncomeService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    IncomeMapper incomeMapper;

    @Autowired
    MemberMapper memberMapper;


    @Override
    public Double sumIncome() {
        return incomeMapper.sumIncome();
    }

    /**
     * 发放佣金  发直推的
     * @param order
     * @param member
     * @param grade
     * @param recordType
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void addIncomeToUser(Order order, Member member, Integer floor, Integer grade, Integer recordType) {
        //直推产品才需要进行反直推的钱
        if ((int)order.getOrderType()==(int)CommodityType.ONE || (int)order.getOrderType()==(int)CommodityType.JICHA){
            logger.debug("进行直推返佣金，直推订单号为"+order.getId());
            Member member1 = memberMapper.findById(member.getId());
            Income income = new Income();
            income.setFloor(floor);
            income.setGrade(grade);
            income.setMoney(order.getRebate());
            income.setOrderId(order.getId());
            income.setNote("直推佣金奖励");
            income.setTargetRichUserId(member.getId());
            income.setIncomeType(IncomeType.ZHITUI);
            incomeMapper.insert(income);
            member1.setWallet(member.getWallet()+order.getRebate());
            memberMapper.updateWallet(member);
            return;
        }
        logger.error("错误跳入 进行直推返佣金，直推订单号为"+order.getId());
        return;
    }

    /**
     * 发放佣金
     * @param member 目标用户
     * @param income   收入
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public synchronized void addIncomeToUser(Member member, Income income) {
        Member member1 = memberMapper.findById(member.getId());
        incomeMapper.insert(income);
        member1.setWallet(member1.getWallet()+income.getMoney());
        memberMapper.updateWallet(member1);
    }

    /**
     * 级差版本
     *
     * @param member
     * @param income
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void addIncomeToUserV1(Member member, Income income) {
        // 先查有没，没有再返
        Income dbIncome = incomeMapper.findIncomeByOrderIdAndTargetIdAndIncomeTypeAndFloor(income);
        if (Objects.isNull(dbIncome)){
            Member member1 = memberMapper.findById(member.getId());
            incomeMapper.insert(income);
            member1.setWallet(member1.getWallet()+income.getMoney());
            memberMapper.updateWallet(member1);
        }
    }

    /**
     * 发放建点佣金
     * @param order
     * @param member
     * @param floor
     * @param grade
     * @param recordType
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void addIncomeJDToUser(Order order, Member member, Integer floor, Integer grade, Integer recordType) {
        if ((int)order.getOrderType()== (int)CommodityType.JICHA){
            //查询出来

            //更新上去



        }else{
            return;
        }
    }

    @Override
    public Double sumUserIncome(Integer id) {
        return incomeMapper.sumPriceByRichUserId(id);
    }

    /**
     * 查找直推单数
     *
     * @param richUserId
     * @return
     */
    @Override
    public int findZhitui(Integer richUserId) {
        return incomeMapper.sumZhiTui(richUserId);
    }

    /**
     * 团队业绩
     *
     * @param targetUserId
     * @return
     */
    @Override
    public int sumTeam(Integer targetUserId) {
        return incomeMapper.sumTeam(targetUserId);
    }

    /**
     * 删除用户佣金
     *
     * @param income
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void deleteUserIncome(Income income) {
        Income temp = incomeMapper.findById(income.getId());
        Member member = memberMapper.findById(temp.getTargetRichUserId());
        //防止数据出错
        if (Objects.nonNull(member)){
            member.setWallet(member.getWallet()-temp.getMoney());
            memberMapper.updateWallet(member);
        }else{
            logger.error("删除的收入记录用户不存在！");
        }
        temp.setDeleteTime(DateUtils.dateToString(new Date()));
        incomeMapper.update(income);
    }

    @Override
    public List<IncomeVo> findMyIncome(Income income, Pager pager) {
        long countAll = countMyIncome(income);
        pager = getPager(countAll,pager);
        return incomeMapper.findMyIncome(income,pager);
    }

    @Override
    public long countMyIncome(Income income) {
        return incomeMapper.countMyIncome(income);
    }


    @Override
    public double sumDayIncome(Income income) {
        return incomeMapper.sumDayIncome(income);
    }

    /**
     * 直推单数
     * @param member
     * @return
     */
    @Override
    public int findJichaZhiTui(Member member) {
        return incomeMapper.findJichaZhiTui(member.getId());
    }

    /**
     * 团队单数 会有重复
     * @param member
     * @return
     */
    @Override
    public int findJiChaTuanDui(Member member) {
        return incomeMapper.findJiChaTuanDui(member.getId());
    }

    @Override
    public int findJiChaOthers(Member member) {
        return incomeMapper.sumOthers(member.getId());
    }

    /**
     * 直推
     *
     * @param member
     * @return
     */
    @Override
    public int findOneFloorOrder(Member member) {
        return incomeMapper.findOneFloorOrder(member.getId());
    }

    @Override
    public int secondFloorOrder(Member member) {
        return incomeMapper.secondFloorOrder(member.getId());
    }

    @Override
    public int teamOrder(Member member) {
        return incomeMapper.teamOrder(member.getId());
    }

    @Override
    public List<IncomeNameVo> loadAll(IncomeNameVo incomeNameVo, Pager pager) {
        long countAll = incomeMapper.count(incomeNameVo);
        pager = getPager(countAll,pager);
        return incomeMapper.findVoByPage(incomeNameVo, pager);
    }


}
