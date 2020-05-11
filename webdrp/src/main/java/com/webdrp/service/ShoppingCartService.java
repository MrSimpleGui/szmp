package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.entity.Member;
import com.webdrp.entity.ShoppingCart;

import java.util.List;

/**
 * @Author: zhang yuan ming
 * @Date: create in 上午12:24 2018/8/29
 * @mail: zh878998515@gmail.com
 * @Description:
 */
public interface ShoppingCartService extends BaseService<ShoppingCart>{

    List<ShoppingCart> findByRichUserId(Integer richUserId);

    /**
     * 加入购物车
     * @param member
     * @param packageId
     * @param count
     * @return
     */
    void addToCart(Member member,Integer packageId,int count);

    /**
     * 从购物车删除
     * @param member
     * @param cartId
     */
    void delByCart(Member member, Integer cartId);

    /**
     * 更新购买数量
     * @param cart
     */
    void updateBuyCount(ShoppingCart cart);

    /**
     * 根据购物车ID进行查找
     * @param ids
     * @return
     */
    List<ShoppingCart> findByCardIds(Integer []ids);
}
