package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.entity.Commodity;
import com.webdrp.entity.CommodityStyle;
import com.webdrp.entity.Order;
import com.webdrp.entity.OrderDetail;
import com.webdrp.common.Pager;
import com.webdrp.err.BusinessException;

import java.util.List;


public interface OrderDetailService extends BaseService<OrderDetail> {

    List<OrderDetail> loadAll(OrderDetail orderDetail, Pager pager);

    /**
     * 插入订单详情
     *
     * @param orderDetail
     */
    void insert(OrderDetail orderDetail);


    /**
     * 更新订单详情信息
     *
     * @param orderDetail
     */
    void update(OrderDetail orderDetail);


    /**
     * 删除订单详情
     *
     * @param orderDetail
     */
    void delete(OrderDetail orderDetail);

    /**
     * 获取订单详情
     *
     * @param orderDetail
     * @return
     */
    OrderDetail detail(OrderDetail orderDetail);
}
