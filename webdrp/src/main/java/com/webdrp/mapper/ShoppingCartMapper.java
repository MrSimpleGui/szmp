package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.entity.ShoppingCart;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: zhang yuan ming
 * @Date: create in 上午12:21 2018/8/29
 * @mail: zh878998515@gmail.com
 * @Description:
 */
@Component
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart>{

    List<ShoppingCart> findByRichUserId(Integer richUserId);

    /**
     * 根据用户ID还有购物车ID删除某条记录
     * @param shoppingCart
     */
    void delByUserIdAndCartId(ShoppingCart shoppingCart);

    /**
     * 更新购买数量
     * @param cart
     */
    void updateBuyCount(ShoppingCart cart);

    /**
     * 购物车增加
     * @param ids
     * @return
     */
    List<ShoppingCart> findByCardIds(@Param("ids") List<Integer> ids);
}
