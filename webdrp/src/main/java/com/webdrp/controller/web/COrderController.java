package com.webdrp.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.payment.mch.MchPayRequest;
import com.webdrp.annotation.PBUserAnnotation;
import com.webdrp.annotation.WechatRequest;
import com.webdrp.common.Pager;
import com.webdrp.common.Result;
import com.webdrp.constant.OrderStatus;
import com.webdrp.entity.Config;
import com.webdrp.entity.Order;
import com.webdrp.entity.Member;
import com.webdrp.entity.*;
import com.webdrp.entity.dto.LogisticsDto;
import com.webdrp.entity.dto.OrderDto;
import com.webdrp.entity.vo.CommodityStyleVo;
import com.webdrp.entity.vo.OrderVo;
import com.webdrp.err.BusinessException;
import com.webdrp.log.annnotion.SystemControllerLog;
import com.webdrp.service.*;
import com.webdrp.util.CusAccessObjectUtil;
import com.webdrp.util.DoubleUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * @Author: zhang yuan ming
 * @Date: create in 23:21 2020-02-18
 * @mail: zh878998515@gmail.com
 * @Description:
 */
@RestController
@RequestMapping("/wechat/api")
public class COrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    ConfigService configService;

    @Autowired
    MemberService memberService;

    @Autowired
    CommodityStyleService commodityStyleService;

    @Autowired
    CommodityService commodityService;

    @SystemControllerLog(description = "微信创建订单支付")
    @ApiOperation("微信创建订单支付")
    @PostMapping("/createOrderAndPay")
    public Result createOrderAndPay(HttpServletRequest request, @RequestParam Integer packageId, @RequestParam int count, @RequestParam String takeName, @RequestParam String takeTel, @RequestParam String address, @PBUserAnnotation Member member) throws WeixinException {

        Config search = new Config();
        search.setConfigKey("CANPAY");
        Config config = configService.findCanPay(search);
        if (config.getStatus().intValue()!=1){
            throw new BusinessException("系统更新，停止支付！");
        }
        //  更新用户默认地址
        Member item = memberService.findOne(member.getId());
        item.setRealName(takeName);
        item.setPhone(takeTel);
        item.setAddress(address);
        memberService.updateAddress(item);

        Order order = orderService.createOrder(packageId, member,count,takeName,takeTel,address);
        MchPayRequest mchPayRequest = paymentService.placeOrder(order, CusAccessObjectUtil.getIpAddress(request), member);
        return Result.success().addAttribute("payMsg", JSONObject.parse(mchPayRequest.toRequestString())).addAttribute("orderId",order.getId());
    }

    @WechatRequest
    @ApiOperation("继续支付")
    @PostMapping("/continueToPay")
    public Result payByOrder(HttpServletRequest request, @PBUserAnnotation Member member, @RequestParam Integer id) throws WeixinException {
        Order order = orderService.findOne(id);
        MchPayRequest mchPayRequest = paymentService.placeOrder(order, CusAccessObjectUtil.getIpAddress(request), member);
        return Result.success().addAttribute("payMsg", JSONObject.parse(mchPayRequest.toRequestString())).addAttribute("orderId",order.getId());
    }

    @WechatRequest
    @SystemControllerLog(description = "微信根据购物车ID创建订单支付")
    @ApiOperation("微信根据购物车ID创建订单支付")
    @PostMapping("/createOrderAndPayByCartIds")
    public Result createOrderAndPayByCartIds(HttpServletRequest request,@RequestParam Integer []cartIds, @RequestParam String takeName, @RequestParam String takeTel, @RequestParam String address, @PBUserAnnotation Member member) throws WeixinException {

        Config search = new Config();
        search.setConfigKey("CANPAY");
        Config config = configService.findCanPay(search);
        if (config.getStatus().intValue()!=1){
            throw new BusinessException("系统更新，停止支付！");
        }
        //  更新用户默认地址
        Member item = memberService.findOne(member.getId());
        item.setRealName(takeName);
        item.setPhone(takeTel);
        item.setAddress(address);
        memberService.updateAddress(item);
        Order order = orderService.createOrder(cartIds, member,takeName,takeTel,address);
        for (Integer id:cartIds){
            shoppingCartService.delByCart(member,id);
        }

        MchPayRequest mchPayRequest = paymentService.placeOrder(order, CusAccessObjectUtil.getIpAddress(request), member);
        return Result.success().addAttribute("payMsg", JSONObject.parse(mchPayRequest.toRequestString())).addAttribute("orderId",order.getId());
    }


    @WechatRequest
    @ApiOperation("取消订单")
    @PostMapping("/cancelOrder")
    public Result cancelOrder(@PBUserAnnotation Member member, @RequestParam Integer id){
        Order order = orderService.findOne(id);
        if (order.getRichUserId().intValue() != member.getId().intValue()){
            return Result.fail("非法取消订单！",500);
        }
        if (order.getStatus().intValue() == OrderStatus.TODO.intValue()){
            order.setStatus(OrderStatus.CANCEL);
            orderService.updateOrderStatus(order);
            return Result.success();
        }
        return Result.fail("已支付订单请联系客服进行取消！",400);
    }




    @ApiOperation("我的订单")
    @GetMapping("/orderList")
    @WechatRequest
    @SystemControllerLog(description = "我的订单 分页")
    public Result orderList(@PBUserAnnotation Member member, Pager pager){
        OrderDto order = new OrderDto();
        order.setRichUserId(member.getId());
        List<OrderVo> list = orderService.loadAllAndDetail(order,pager);
        return Result.success().addAttribute("list",list).addAttribute("pager",pager);
    }

    @ApiOperation("我的订单")
    @GetMapping("/orderListByStatus")
    @WechatRequest
    @SystemControllerLog(description = "我的订单 分页")
    public Result orderListByStatus(@PBUserAnnotation Member member, OrderDto order, Pager pager){
        order.setRichUserId(member.getId());
        List<OrderVo> list = orderService.loadAllAndDetail(order,pager);
        return Result.success().addAttribute("list",list).addAttribute("pager",pager);
    }

    @ApiOperation("订单详情")
    @GetMapping("/orderDetail")
    @WechatRequest
    @SystemControllerLog(description = "订单详情")
    public Result orderDetail(@PBUserAnnotation Member member, @RequestParam Integer id){
        Order order = orderService.findByWechatIdAndOrderId(id,member.getId());
        if (Objects.isNull(order)){
            return Result.fail("非个人订单，无法访问",400);
        }
        OrderVo orderVo = orderService.detail(order);
        LogisticsDto dto = null;
        if (!StringUtils.isEmpty(order.getWuliuId())){
            try {
                dto = orderService.getLogisticsMsg(order.getWuliuId());
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        Result result = Result.success().addAttribute("order",orderVo);
        if (Objects.nonNull(dto)&&Objects.nonNull(dto.getResult())){
            result.addAttribute("logistics",dto.getResult().getList());
        }else{
            result.addAttribute("logistics",dto);
        }

        return result;
    }

    @ApiOperation("APP下订单")
    @GetMapping("/appPrepay")
    @WechatRequest
    @SystemControllerLog(description = "APP订单")
    public Result appPrepay(@PBUserAnnotation Member richUser, @RequestParam Integer packageId,@RequestParam Integer count){
        Result result = Result.success();

        CommodityStyle commodityStyle = new CommodityStyle();
        commodityStyle.setId(packageId);
        CommodityStyleVo detail = commodityStyleService.detail(commodityStyle);


        Member user = memberService.findOne(richUser.getId());


        // 是否有地址
        boolean hasAddress = true;
        if (StringUtils.isEmpty(user.getPhone())){
            hasAddress = false;
        }

        boolean noExpress = true;

        Commodity commodity = commodityService.findOne(detail.getCommodityId());
        if (Objects.nonNull(commodity.getExpress())){
            if (commodity.getExpress().doubleValue() > 0){
                noExpress = false;
                result.addAttribute("express",commodity.getExpress());
            }
        }

        //判断用户等级（改级卡只有粉丝能用）
        if(commodity.getName().equals("改级卡") && user.getGrade() != 0){
            throw new BusinessException("只有粉丝才能购买改级卡！");
        }

        return result
                .addAttribute("commodity",commodity)
                .addAttribute("package",detail)
                .addAttribute("count",count)
                .addAttribute("phone",user.getPhone())
                .addAttribute("takeName",user.getRealName())
                .addAttribute("address",user.getAddress())
                .addAttribute("hasAddress",hasAddress)
                .addAttribute("subPrice",detail.getPrice()*count)
                .addAttribute("noExpress",noExpress);
    }

    @Autowired
    ShoppingCartService shoppingCartService;

    @ApiOperation("通过购物车ID进行预结算")
    @GetMapping("/appPrepayByCartIds")
    @WechatRequest
    @SystemControllerLog(description = "通过购物车ID进行结算")
    public Result appPrepayByCartIds(@PBUserAnnotation Member richUser, @RequestParam Integer []ids){
        Result result = Result.success();
        Member user = memberService.findOne(richUser.getId());

        // 是否有地址
        boolean hasAddress = true;
        if (StringUtils.isEmpty(user.getPhone())){
            hasAddress = false;
        }

        List<ShoppingCart> list =shoppingCartService.findByCardIds(ids);
        double subPrice = 0.0d;
        for (ShoppingCart item:list){
            subPrice = subPrice + (item.getPrice() * item.getInventory());
        }



        boolean noExpress = true;

        return result.addAttribute("packages",list)
                .addAttribute("phone",user.getPhone())
                .addAttribute("takeName",user.getRealName())
                .addAttribute("address",user.getAddress())
                .addAttribute("hasAddress",hasAddress)
                .addAttribute("subPrice", DoubleUtil.formatDouble(subPrice))
                .addAttribute("noExpress",noExpress);
    }






}
