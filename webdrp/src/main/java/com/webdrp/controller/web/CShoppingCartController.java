package com.webdrp.controller.web;

import com.webdrp.annotation.PBUserAnnotation;
import com.webdrp.annotation.WechatRequest;
import com.webdrp.common.Pager;
import com.webdrp.common.Result;
import com.webdrp.entity.Member;
import com.webdrp.entity.ShoppingCart;
import com.webdrp.log.annnotion.SystemControllerLog;
import com.webdrp.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: zhang yuan ming
 * @Date: create in 12:53 2020-03-20
 * @mail: zh878998515@gmail.com
 * @Description:
 */
@Api(tags = "购物车")
@RestController
@RequestMapping("/wechat/api")
public class CShoppingCartController {

    @Autowired
    ShoppingCartService shoppingCartService;


    @SystemControllerLog(description = "购物车列表")
    @WechatRequest
    @ApiOperation("购物车列表")
    @GetMapping("/cartList")
    public Result cartList(@PBUserAnnotation Member member, Pager pager){
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setRichUserId(member.getId());
        List<ShoppingCart> list = shoppingCartService.loadAll(shoppingCart,pager);
        return  Result.success().addAttribute("list",list).addAttribute("pager",pager);
    }

    @SystemControllerLog(description = "加入购物车")
    @WechatRequest
    @ApiOperation("加入购物车")
    @PostMapping("/addCart")
    public Result addCart(@PBUserAnnotation Member member, @RequestParam Integer packageId,@RequestParam int count){
        shoppingCartService.addToCart(member,packageId,count);
        return Result.success();
    }

    @SystemControllerLog(description = "从购物车删除")
    @WechatRequest
    @ApiOperation("从购物车删除")
    @PostMapping("/delCart")
    public Result delCart(@PBUserAnnotation Member member, @RequestParam Integer []cartId){
        for (Integer id:cartId){
            shoppingCartService.delByCart(member,id);
        }
        return Result.success();
    }

    @SystemControllerLog(description = "更新某个产品购物车中的购买数量")
    @WechatRequest
    @ApiOperation("更新某个产品的购买数量")
    @PostMapping("/updateBuyCount")
    public Result updateBuyCount(@PBUserAnnotation Member member,@RequestParam Integer id,@RequestParam int count){
        if (count <= 0 ){
            return Result.success();
        }
        ShoppingCart cart = shoppingCartService.findOne(id);
        if (cart.getRichUserId().intValue() == member.getId().intValue()){
            cart.setInventory(count);
            shoppingCartService.updateBuyCount(cart);
        }
        return Result.success();
    }









}
