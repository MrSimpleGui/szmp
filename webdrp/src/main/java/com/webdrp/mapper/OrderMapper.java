package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.common.Pager;
import com.webdrp.entity.Order;
import com.webdrp.entity.OrderDetail;
import com.webdrp.entity.Record;
import com.webdrp.entity.dto.OrderDto;
import com.webdrp.entity.vo.OrderVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;


@Component
public interface OrderMapper extends BaseMapper<Order> {


    Order findByWechatIdAndOrderId(@Param("id")Integer id,@Param("richUserId")Integer richUserId);

    /**
     * 根据微信订单号以及订单号查询订单
     * @param id
     * @param wechatOrderId
     * @return
     */
    Order findByWechatOrderIdAndOrderId(@Param("id")Integer id,@Param("wechatOrderId") String wechatOrderId);

    List<OrderVo> findAllAndDetail(@Param("entity") OrderDto order,@Param("pager") Pager pager);

    long findAllAndDetailCount(@Param("entity") OrderDto order);

    OrderVo findById(Serializable id);

    Order queryById(Integer id);

    void updateWechatOrderId(Order order);

    void updateOrderStatus(Order order);

    void cancelOrderByOrderIdAndRichUserId(Order order);


    List<OrderVo> exportOrderData(OrderDto orderDto);

//    统计订单数量，按照时间统计
    int findCountOrderData(OrderDto orderDto);
//统计订单总金额
    double findSumOrderPrice(OrderDto orderDto);

    /**
     * 支持通过状态查询
     * @param order
     * @return
     */
    Double sumOrderMoney(Order order);

    void updatezfbOrder(Order order);

    Order findByzfbOrderId(String zfbOrderId);


    void batchUpdateWuliuId(List<Order> list);

    int findUserOrderCount(Order order);

    /**
     * 更新订单类型
     * @param order
     */
    void updateType(Order order);
}
