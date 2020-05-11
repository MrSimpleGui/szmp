package com.webdrp.service.impl;

import com.webdrp.constant.IncomeType;
import com.webdrp.entity.*;
import com.webdrp.err.BusinessException;
import com.webdrp.mapper.*;
import com.webdrp.service.IncomeService;
import com.webdrp.util.DoubleUtil;
import com.webdrp.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PayIncomeAndUserV10UpGradeService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private GradeMapper gradeMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private CommissionRuleMapper commissionRuleMapper;

    @Lazy
    @Autowired
    private IncomeMapper incomeMapper;

    /**
    * @Description 计算分佣，用户升级 先计算购买者升级，再一二级分佣，接着团队奖，最后算平级
    * @Param order
    * @Return
    * @Author Mr.Simple
    * @Date 2020/3/12 0012 下午 5:48
    **/
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public synchronized void payIncomeV10AndUserUpGrade(Order order){
        //判断该订单是否已经返佣
        Income income = new Income();
        income.setOrderId(order.getId());
        int incomeCount = incomeMapper.findCountByOrderId(income);
        if(incomeCount > 0){
            log.debug("订单【" + order.getId() + "】已参与过返佣");
            return;
        }
        // 购买者
        Member temp = memberMapper.findById(order.getRichUserId());
        //查出所有等级
        List<Grade> gradeList = gradeMapper.findAll(new Grade());
        if(gradeList.size() == 0){
            return;
        }

        //获取该订单的商品明细（已减去快递费）
        List<OrderDetail> orderDetailList = getOrderDetail(order.getId());
        if(orderDetailList == null || orderDetailList.size() == 0){
            return;
        }

        //获取商品id
        List<Integer> commodityIdList = orderDetailList.stream().map(OrderDetail::getCommodityId).distinct()
                                            .collect(Collectors.toList());
        if(commodityIdList == null || commodityIdList.size() == 0){
            return;
        }
        //获取商品信息
        List<Commodity> commodityList = commodityMapper.findCommodityByIds(commodityIdList);

        // 购买者升级判断
        if(temp.getGrade().intValue() == 0){
            //筛选出购买者当前等级规则
            List<Grade> currentGrade = gradeList.stream().filter(item->item.getRank().equals(temp.getGrade()))
                    .collect(Collectors.toList());
            updateGrade(temp, order.getId(), currentGrade.get(0), commodityList);
        }

        //查出该订单所有商品的分佣模式并加载所有角色的分佣规则
        List<Integer> commissionModeList = commodityList.stream().map(Commodity::getcType).distinct()
                                            .collect(Collectors.toList());
        List<CommissionRule> commissionRuleList = commissionRuleMapper.findRuleByModeIds(commissionModeList);
        if(commissionRuleList.size() == 0){
            return;
        }


        //获取所有推荐路径用户
        List<Member> list = getBuyerRecommender(temp);

        //根据订单明细单次计算一二级分佣、各类奖励
        orderDetailList.forEach(item->{
            //获取该订单商品的分佣模式
            //一二级分佣 不同等级对应不同的分佣规则
            Map<Integer, CommissionRule> currentRuleMap = new HashMap<>();
            //团队、平级分佣
            Map<Integer, Integer> teamRewardMap = new HashMap<>();
            Map<Integer, Integer> sidewayMap = new HashMap<>();
            log.debug("commodityId:{}", item.getCommodityId());
            //获取订单商品信息
            Commodity currentCommodity = commodityList.stream()
                    .filter(commodity -> commodity.getId().equals(item.getCommodityId()))
                    .collect(Collectors.toList()).get(0);
            if(currentCommodity.getcType() != 0){
                //根据商品的分佣模式获取该模式下所有角色的分佣规则
                List<CommissionRule> currentRuleList = commissionRuleList.stream()
                        .filter(commissionRule -> commissionRule.getModeId().equals(currentCommodity.getcType()))
                        .collect(Collectors.toList());
                currentRuleList.forEach(ruleList->{
                    currentRuleMap.put(ruleList.getGradeRank(), ruleList);
                    if(ruleList.getTeamReward() != null && ruleList.getTeamReward() != 0){
                        teamRewardMap.put(ruleList.getGradeRank(), 0);
                    }
                    if(ruleList.getSidewayNumber() != null && ruleList.getSidewayNumber() != 0){
                        sidewayMap.put(ruleList.getGradeRank(), ruleList.getSidewayNumber());
                    }
                });
                log.debug("会员团队资格:{}", teamRewardMap.get(1));
                //通过该条明细、角色规则对应、购买者信息计算一二级分佣
                secondRecord(temp, item, currentRuleMap);
                //计算分佣 需要所有推荐人，该商品对应的分佣模式
                reward(temp, currentRuleMap, teamRewardMap, sidewayMap, list, item);
            }
        });

        upGrade(list, gradeList);
    }

    /**
    * @Description 给自己升级 前序已判断过等级
    * @Param member, orderId
    * @Return
    * @Author Mr.Simple
    * @Date 2020/3/12 0012 下午 2:42
    **/
    public void updateGrade(Member member, Integer orderId, Grade grade, List<Commodity> commodityList) {
        //判断是否需要购买指定商品升级
        if(Objects.isNull(grade.getSpecificMode()) || grade.getSpecificMode().equals("0")){
            member.setGrade(1);
            memberMapper.updateRichUserGrade(member);
            log.debug("【updateGrade】个人购买升级，订单Id="+orderId);
            return;
        }
        //获取商品分佣模式
        /* = commodityList.stream().filter(item->item.getcType()
                                        .equals(grade.getSpecificMode())).collect(Collectors.toList())*/;
        //拆分出指定模式

        List<Integer> modes = StringUtils.strToInteger(grade.getSpecificMode());
        boolean isGrade = false;
        if(modes.size() != 0){
            for (Integer mode : modes) {
                List<Commodity> selectedList = commodityList.stream().filter(item->item.getcType().equals(mode))
                        .collect(Collectors.toList());
                if(selectedList.size() != 0){
                    isGrade = true;
                    break;
                }
            }
        }

        if(isGrade) {
            member.setGrade(1);
            memberMapper.updateRichUserGrade(member);
            log.debug("【updateGrade】个人购买升级，订单Id="+orderId);
        } else {
            log.debug("没有购买指定商品，不予升级");
        }
    }

    /**
    * @Description 根据单条明细计算一二级分销
    * @Param buyer, orderDetail, ruleMap
    * @Return
    * @Author Mr.Simple
    * @Date 2020/3/13 0013 下午 5:51
    **/
    public void secondRecord(Member buyer, OrderDetail orderDetail, Map<Integer,CommissionRule> ruleMap){
        Member currentUser = buyer;
        for (int i = 0 ; i < 2;i++){
            Member target = memberMapper.findById(currentUser.getZid());
            if (Objects.nonNull(target) && target.getGrade().intValue() > 0){
                //获取该用户角色的分佣规则
                CommissionRule currentRule = ruleMap.get(target.getGrade());

                Income toUser = new Income();
                // 一级二级分红要根据商品分别计算
                int floor = i + 1;
                //计算分佣
                Double commissionMoney = calculationCommission(currentRule, orderDetail, floor);
                if (i==0){
                    toUser = new Income(orderDetail.getOrderId(),target.getId(),buyer.getId(),"直推购买奖金",
                            commissionMoney, target.getGrade(),floor,1, IncomeType.FENXIAO);
                }
                // 二级
                if (i==1){
                    toUser = new Income(orderDetail.getOrderId(), target.getId(), buyer.getId(), "销售购买奖金",
                            commissionMoney, target.getGrade(), floor, 1, IncomeType.FENXIAO);
                }
                incomeService.addIncomeToUser(target,toUser);
                currentUser = target;
            }
        }
    }

    /**
    * @Description 计算团队奖和平级奖
    * @Param buyer, teamRewardMap, sidewayMap, memberList
    * @Return
    * @Author Mr.Simple
    * @Date 2020/3/16 0016 上午 10:22
    **/
    public void reward(Member buyer, Map<Integer, CommissionRule> currentRuleMap,
                       Map<Integer, Integer> teamRewardMap, Map<Integer, Integer> sidewayMap,
                       List<Member> memberList, OrderDetail orderDetail){
        //团队、平级分佣
        int selectedRank = 0;
        int floor = 0;
        //memberList已排好序，循环memberList计算金额
        for (Member member : memberList) {
            floor += 1;
            //获取当前用户的grand值
            int currentGrandRank = member.getGrade();
            //获取该用户角色的分佣规则
            CommissionRule currentRule = currentRuleMap.get(currentGrandRank);
            //防止降级分成
            if(currentRule != null && currentGrandRank >= selectedRank){
                selectedRank = currentGrandRank;
                //计算团队奖和平级奖
                Map<Integer, Double> result = calTeamAndLevelReward(currentRule, currentGrandRank, orderDetail,
                        teamRewardMap, sidewayMap);
                if(result.size() != 0){
                    Income toUser = new Income();

                    //团队管理奖
                    if(result.get(IncomeType.TEAM_MANAGER) != null){
                        log.debug("commission:{}", result.get(IncomeType.TEAM_MANAGER));
                        toUser = new Income(orderDetail.getOrderId(), member.getId(), buyer.getId(),
                                IncomeType.getIncomeTypeString(IncomeType.TEAM_MANAGER),
                                result.get(IncomeType.TEAM_MANAGER), member.getGrade(), floor, 1,
                                IncomeType.TEAM_MANAGER);
                    }
                    if(result.get(IncomeType.SIDE_WAY) != null){
                        log.debug("commission:{}", result.get(IncomeType.SIDE_WAY));
                        toUser = new Income(orderDetail.getOrderId(), member.getId(), buyer.getId(),
                                IncomeType.getIncomeTypeString(IncomeType.SIDE_WAY),
                                result.get(IncomeType.SIDE_WAY), member.getGrade(), floor, 1,
                                IncomeType.SIDE_WAY);
                    }
                    incomeService.addIncomeToUser(member,toUser);
                }
            }
        }

    }

    public void upGrade(List<Member> memberList, List<Grade> gradeList){
        //获取当前最高等级
        gradeList.sort((s2,s1)->s1.getRank().compareTo(s2.getRank()));
        int highestLevel = gradeList.get(0).getRank();
        memberList.forEach(member -> {
            //获取该用户的直推人数和团队销售单数
            // 根据当前等级
            //当用户是粉丝时，不予升级
            if(member.getGrade() != 0){
                int recommend = incomeService.findOneFloorOrder(member);
                int team = memberMapper.findTeamUserOrderCountV1(member);
                //获取当前用户的级别信息
                Grade grade = gradeList.stream()
                        .filter(item->item.getRank().equals(member.getGrade()))
                        .collect(Collectors.toList()).get(0);
                if(grade != null && grade.getRank() < highestLevel){
                    //判断用户是否符合升级条件
                    if(recommend >= grade.getRecommendNumber() || team >= grade.getTeamNumber()){
                        member.setGrade(member.getGrade() + 1);
                        memberMapper.updateRichUserGrade(member);
                    }
                }
            }
        });
    }

    /**
    * @Description 计算团队奖、平级奖
    * @Param rule, teamRewardMap, sidewayMap
    * @Return
    * @Author Mr.Simple
    * @Date 2020/3/16 0016 上午 11:08
    **/
    private Map<Integer, Double> calTeamAndLevelReward(CommissionRule rule, Integer currentGrandRank,
                OrderDetail orderDetail, Map<Integer, Integer> teamRewardMap, Map<Integer, Integer> sidewayMap){
        Map<Integer, Double> result = new HashMap<>();
        //判断当前用户是否可分团队奖
        Integer teamReward = teamRewardMap.get(currentGrandRank);
        log.debug("teamReward:{}", teamReward);
        if(teamReward != null && teamReward.intValue() != 1){
            log.debug("===============团队管理奖！");
            //计算团队奖 分为百分比和金额两种形式
            teamRewardMap.put(currentGrandRank, 1);
            result.put(IncomeType.TEAM_MANAGER,
                    calculationLevel(rule.getTeamReward(), rule.getType(), orderDetail));
            return result;
        }
        //判断是否可分评级奖
        Integer sidewayNumber = sidewayMap.get(currentGrandRank);
        if(sidewayNumber != null && sidewayNumber.intValue() != 0){
            sidewayMap.put(currentGrandRank, --sidewayNumber);
            result.put(IncomeType.SIDE_WAY,
                    calculationLevel(rule.getSidewayCommission(), rule.getType(), orderDetail));
            return result;
        }
        return result;
    }

    /**
    * @Description 查出订单非快递费明细
    * @Param orderId
    * @Return
    * @Author Mr.Simple
    * @Date 2020/3/13 0013 上午 11:56
    **/
    private List<OrderDetail> getOrderDetail(Integer orderId){
        //根据orderId查出orderDetails，筛选出所有商品的东西
        List<OrderDetail> orderDetailList = orderDetailMapper.findByOrderId(orderId);
        if(orderDetailList.size() == 0){
            return null;
        }

        return orderDetailList.stream().filter(item->!item.getNameItem().equals("快递费"))
                .collect(Collectors.toList());
    }


    private Double calculationCommission(CommissionRule commissionRule, OrderDetail orderDetail, Integer floor){
        Double amount = 0d;
        switch (floor.intValue()){
            case 1:
                //计算一级分佣
                amount = calculationLevel(commissionRule.getFirstLevel(), commissionRule.getType(), orderDetail);
                break;
            case 2:
                amount = calculationLevel(commissionRule.getSecondLevel(), commissionRule.getType(), orderDetail);
                break;
            default:
                break;
        }
        log.debug("amount:{}", amount);
        return amount;
    }

    /**
    * @Description 分销的返佣
    * @Param level, type, orderDetail
    * @Return
    * @Author Mr.Simple
    * @Date 2020/3/13 0013 下午 6:16
    **/
    private Double calculationLevel(Double level, Integer type, OrderDetail orderDetail){
        Double commissionAmount = 0d;
        switch (type.intValue()){
            case 0:
                //根据百分比计算商品分佣
                commissionAmount = DoubleUtil.add(commissionAmount,
                        calculationByProp(level, orderDetail.getSubPrice()));
                break;
            case 1:
                commissionAmount = DoubleUtil.add(commissionAmount,
                        DoubleUtil.mul(level, new Double(orderDetail.getCount())));
                break;
            default:
                break;
        }
        log.debug("commissionAmount:{}", commissionAmount);
        return commissionAmount;

    }

    private Double calculationByProp(Double levelProp, Double subPrice){
        return DoubleUtil.mul(levelProp, subPrice);
    }

    /**
    * @Description 获取用户的全部推荐人，并按照推荐路径排序
    * @Param buyer
    * @Return
    * @Author Mr.Simple
    * @Date 2020/3/14 0014 下午 2:57
    **/
    private List<Member> getBuyerRecommender(Member buyer){
        String[] recommender = buyer.getPath().split(",");
        List<Member> sortList = new ArrayList<>();
        // 获取购买者的全部上级
        List<Member> list = memberMapper.findByIds(recommender);
        if(list.size() != 0){
            List<Integer> memberIdList = list.stream().map(Member::getId).collect(Collectors.toList());
            for (int i = recommender.length - 1; i >= 0; i--) {
                int currentId = Integer.parseInt(recommender[i]);
                if(memberIdList.contains(currentId)){
                    sortList.add(
                            list.stream().filter(item->item.getId().equals(currentId)).collect(Collectors.toList()).get(0)
                    );
                }
            }
        }

        return sortList;
    }


}
