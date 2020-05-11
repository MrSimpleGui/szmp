package com.webdrp.controller.alipay;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.payment.mch.MchPayRequest;
import com.webdrp.annotation.PBUserAnnotation;
import com.webdrp.annotation.WechatRequest;
import com.webdrp.common.Result;
import com.webdrp.entity.Config;
import com.webdrp.entity.Member;
import com.webdrp.entity.Order;
import com.webdrp.entity.ShoppingCart;
import com.webdrp.err.BusinessException;
import com.webdrp.log.annnotion.SystemControllerLog;
import com.webdrp.service.*;
import com.webdrp.service.MemberService;
import com.webdrp.service.impl.AlipayServiceImpl;
import com.webdrp.util.CusAccessObjectUtil;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * @Author: zhang yuan ming
 * @Date: create in 15:59 2020-03-11
 * @mail: zh878998515@gmail.com
 * @Description: 支付宝创建APP支付
 */

@Api(tags = "APP支付")
@RestController
@RequestMapping("/app/pay")
public class AlipayAppController {

    @Autowired
    MemberService memberService;

    @Autowired
    OrderService orderService;

    @Autowired
    AlipayServiceImpl alipayService;

    @Autowired
    ConfigService configService;

    @Autowired
    ShoppingCartService shoppingCartService;

    @WechatRequest
    @ApiOperation("APP创建订单支付")
    @PostMapping("/createOrderAndPay")
    public Result createOrderAndPay(@RequestParam Integer packageId, @RequestParam int count, @RequestParam String takeName, @RequestParam String takeTel, @RequestParam String address, @PBUserAnnotation Member member){

        Config search = new Config();
        search.setConfigKey("CANPAY");
        Config config = configService.findCanPay(search);
        if (config.getStatus().intValue()!=1){
            throw new BusinessException("系统更新，停止支付！");
        }
        //  更新用户默认地址
        Member item =memberService.findOne(member.getId());
        item.setRealName(takeName);
        item.setPhone(takeTel);
        item.setAddress(address);
        memberService.updateAddress(item);

        Order order = orderService.createOrder(packageId, member,count,takeName,takeTel,address);
        String pay = alipayService.createZfbAppOrder(order);
        return Result.success().addAttribute("payStr", pay);
    }


    @WechatRequest
    @SystemControllerLog(description = "根据购物车ID创建订单支付")
    @ApiOperation("根据购物车ID创建订单支付")
    @PostMapping("/createOrderAndPayByCartIds")
    public Result createOrderAndPayByCartIds(@RequestParam Integer []cartIds, @RequestParam String takeName, @RequestParam String takeTel, @RequestParam String address, @PBUserAnnotation Member member) {

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

        String pay = alipayService.createZfbAppOrder(order);
        return Result.success().addAttribute("payStr", pay);
    }


    @WechatRequest
    @ApiOperation("继续支付")
    @PostMapping("/continueToPay")
    public Result payByOrder(@PBUserAnnotation Member member, @RequestParam Integer id){
        Order order = orderService.findOne(id);
        String pay = alipayService.createZfbAppOrder(order);
        return Result.success().addAttribute("payStr", pay);
    }

}
