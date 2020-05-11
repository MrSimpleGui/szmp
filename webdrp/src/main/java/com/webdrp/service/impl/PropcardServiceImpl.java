package com.webdrp.service.impl;

import com.webdrp.common.BaseServiceImpl;
import com.webdrp.constant.PropcardType;
import com.webdrp.entity.*;
import com.webdrp.entity.vo.PropcardVo;
import com.webdrp.err.BusinessException;
import com.webdrp.mapper.*;
import com.webdrp.service.MemberService;
import com.webdrp.service.PropcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PropcardServiceImpl extends BaseServiceImpl<Propcard, PropcardMapper> implements PropcardService {

    @Autowired
    private PropcardMapper propcardMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private CommodityStyleMapper styleMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberService memberService;

    /**
    * @Description 添加道具卡
    * @Param propcard
    * @Return
    * @Author Mr.Simple
    * @Date 2020/4/13 0013 下午 4:15
    **/
    public void add(Propcard propcard){
        //判断用户是否已有记录
        Propcard selected = propcardMapper.findByUserAndStyle(propcard);
        if(Objects.isNull(selected)){
            //给用户插入数据
            propcard.beforeCreate();
            propcardMapper.insert(propcard);
        } else {
            //更新
            propcard.setCount(selected.getCount() + propcard.getCount());
            super.update(propcard);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void rename(Member member){
        try {
            //先获取用户卡数量
            Propcard selected = new Propcard();
            selected.setType(PropcardType.RENAME);
            selected.setRichUserId(member.getId());
            Propcard propcard = propcardMapper.findByUserAndStyle(selected);
            if(Objects.isNull(propcard) || propcard.getCount() == 0){
                throw new BusinessException("没有可用的改名卡");
            }
            //改名
            memberMapper.updateNickname(member);
            //清空分享二维码
            memberMapper.clearOneQrCode(member.getId());
            propcard.setCount(propcard.getCount() - 1);
            super.update(propcard);
        } catch (BusinessException b) {
            throw b;
        } catch (Exception e) {
            throw new BusinessException("更新失败");
        }
    }

    @Override
    public void resuper(Member member, Integer topId) {
        try {
            //判断用户等级
            if(member.getGrade() != 0){
                throw new BusinessException("用户等级不满足使用条件");
            }
            //先获取用户卡数量
            Propcard selected = new Propcard();
            selected.setType(PropcardType.RESUPER);
            selected.setRichUserId(member.getId());
            Propcard propcard = propcardMapper.findByUserAndStyle(selected);
            if(Objects.isNull(propcard) || propcard.getCount() == 0){
                throw new BusinessException("没有可用的改级卡");
            }
            //改上级
            if(!memberService.updateTopId(member, topId)){
                throw new BusinessException("改级失败");
            }
            propcard.setCount(propcard.getCount() - 1);
            super.update(propcard);
        } catch (BusinessException b) {
            throw b;
        } catch (Exception e) {
            throw new BusinessException("更新失败");
        }

    }

    /**
    * @Description 判断是否有道具并添加记录
    * @Param order
    * @Return
    * @Author Mr.Simple
    * @Date 2020/4/13 0013 下午 5:08
    **/
    public void dealPropcard(Order order){
        //根据订单id获取订单明细
        List<OrderDetail> list = orderDetailMapper.findByOrderId(order.getId());
        if(list.size() != 0){
            List<OrderDetail> detailList = list.stream().filter(item->!item.getNameItem().equals("快递费"))
                    .collect(Collectors.toList());
            //获取商品id
            List<Integer> commodityIdList = detailList.stream().map(OrderDetail::getCommodityId).distinct()
                    .collect(Collectors.toList());
            if(commodityIdList == null || commodityIdList.size() == 0){
                return;
            }
            //获取道具id
            List<Integer> idList = commodityMapper.findCommodityByIds(commodityIdList)
                    .stream()
                    .filter(commodity -> commodity.getClassId() == 1)
                    .map(Commodity::getId)
                    .collect(Collectors.toList());
            if(idList.size() != 0){
                //筛选出与道具相关订单详情
                List<OrderDetail> conditionList = detailList.stream().filter(item->idList.contains(item.getCommodityId()))
                        .collect(Collectors.toList());
                conditionList.forEach(item->{
                    //根据styleid查出商品详情
                    CommodityStyle commodityStyle = styleMapper.findById(item.getCommodityStyleId());
                    if(Objects.nonNull(commodityStyle)){
                        Propcard propcard = new Propcard();
                        for(Map.Entry<Integer, String> entry: PropcardType.map.entrySet()){
                            if(entry.getValue().equals(commodityStyle.getName())){
                                propcard.setType(entry.getKey());
                            }
                        }
                        propcard.setCount(item.getCount());
                        propcard.setRichUserId(order.getRichUserId());
                        propcard.setName(commodityStyle.getName());
                        propcard.setCommodityStyleId(commodityStyle.getId());
                        add(propcard);
                    }
                });
            }

        }
    }

    @Override
    public List<PropcardVo> findPropcard(Member member) {
        //查看用户是否有道具卡
        List<PropcardVo> list = propcardMapper.findVoByMemberId(member.getId());
        if(list.size() == 0){
            initPropcard(member);
        }
        return propcardMapper.findVoByMemberId(member.getId());
    }

    public void initPropcard(Member member){
        //初始化
        //查找道具
        Commodity commodity = new Commodity();
        commodity.setClassId(1);
        List<Commodity> list = commodityMapper.findAll(commodity);
        if(list.size() != 0){
            list.forEach(item->{
                Propcard propcard = new Propcard();
                propcard.setRichUserId(member.getId());
                propcard.setCount(0);
                propcard.setName(item.getName());
                CommodityStyle commodityStyle = new CommodityStyle();
                commodityStyle.setCommodityId(item.getId());
                List<CommodityStyle> styleList = styleMapper.findAll(commodityStyle);
                if(styleList.size() == 0){
                    throw new BusinessException("道具卡不存在");
                }
                propcard.setCommodityId(item.getId());
                propcard.setCommodityStyleId(styleList.get(0).getId());
                if(item.getName().equals("改名卡")){
                    propcard.setType(0);
                } else if(item.getName().equals("改级卡")) {
                    propcard.setType(1);
                }
                add(propcard);
            });
        }
    }


}
