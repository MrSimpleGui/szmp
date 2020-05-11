package com.webdrp.service.impl;

import com.webdrp.common.BaseServiceImpl;
import com.webdrp.entity.Commodity;
import com.webdrp.entity.CommodityStyle;
import com.webdrp.entity.Member;
import com.webdrp.entity.ShoppingCart;
import com.webdrp.entity.vo.CommodityStyleVo;
import com.webdrp.mapper.ShoppingCartMapper;
import com.webdrp.service.CommodityService;
import com.webdrp.service.CommodityStyleService;
import com.webdrp.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @Author: zhang yuan ming
 * @Date: create in 上午12:24 2018/8/29
 * @mail: zh878998515@gmail.com
 * @Description:
 */
@Service
public class ShoppingCartServiceImpl extends BaseServiceImpl<ShoppingCart,ShoppingCartMapper> implements ShoppingCartService{


    @Autowired
    ShoppingCartMapper shoppingCartMapper;

    @Autowired
    CommodityStyleService commodityStyleService;

    @Autowired
    CommodityService commodityService;

    /**
     * 通过用户ID查找他的订单
     * @param richUserId
     * @return
     */
    @Override
    public List<ShoppingCart> findByRichUserId(Integer richUserId) {
        return shoppingCartMapper.findByRichUserId(richUserId);
    }

    /**
     * 加入购物车
     *
     * @param member
     * @param packageId
     * @param count
     * @return
     */
    @Override
    public void addToCart(Member member, Integer packageId, int count) {
        CommodityStyle com = new CommodityStyle();
        com.setId(packageId);
        CommodityStyleVo commodityStyle = commodityStyleService.detail(com);
        if (Objects.isNull(commodityStyle)){
            return;
        }
        Commodity commodity = commodityService.findOne(commodityStyle.getCommodityId());
        if (Objects.isNull(commodity)){
            return;
        }

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setRichUserId(member.getId());
        shoppingCart.setCommodityId(commodity.getId());
        shoppingCart.setCommodityStyleId(packageId);
        shoppingCart.setCommodityStyleName(commodityStyle.getName());
        if (Objects.nonNull(commodityStyle.getImages())){
            shoppingCart.setImageUrl(commodityStyle.getImages().get(0).getFullUrl());
        }
        shoppingCart.setInventory(count);
        shoppingCart.setName(commodity.getName());
        shoppingCart.setNameItem(commodity.getNameItem());
        shoppingCart.setPrice(commodityStyle.getPrice());
        shoppingCartMapper.insert(shoppingCart);
        return;
    }

    /**
     * 从购物车删除
     *
     * @param member
     * @param cartId
     */
    @Override
    public void delByCart(Member member, Integer cartId) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setRichUserId(member.getId());
        shoppingCart.setId(cartId);
        shoppingCartMapper.delByUserIdAndCartId(shoppingCart);
    }

    /**
     * 更新购买数量
     *
     * @param cart
     */
    @Override
    public void updateBuyCount(ShoppingCart cart) {
        shoppingCartMapper.updateBuyCount(cart);
    }

    /**
     * 根据购物车ID进行查找
     *
     * @param ids
     * @return
     */
    @Override
    public List<ShoppingCart> findByCardIds(Integer[] ids) {
        List<Integer> listIds = Arrays.asList(ids);
        return shoppingCartMapper.findByCardIds(listIds);
    }
}
