package com.webdrp.service.impl;
import com.webdrp.common.BaseServiceImpl;
import com.webdrp.entity.Commodity;
import com.webdrp.entity.CommodityStyle;
import com.webdrp.entity.Order;
import com.webdrp.entity.OrderDetail;
import com.webdrp.err.BusinessException;
import com.webdrp.mapper.CommodityMapper;
import com.webdrp.mapper.CommodityStyleMapper;
import com.webdrp.mapper.OrderDetailMapper;
import com.webdrp.mapper.OrderMapper;
import com.webdrp.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yuanming on 2018/8/10.
 */
@Service
public class OrderDetailServiceImpl extends BaseServiceImpl<OrderDetail, OrderDetailMapper> implements OrderDetailService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private CommodityStyleMapper commodityStyleMapper;

    /**
     * 插入订单详情
     *
     * @param orderDetail
     */
    public void insert(OrderDetail orderDetail) {
        Integer orderId = orderDetail.getOrderId();
        Integer commodityId = orderDetail.getCommodityId();
        Integer commodityStyleId = orderDetail.getCommodityStyleId();
        Order order = orderMapper.findById(orderId);
        if (order == null) throw new BusinessException("无该订单！");
        Commodity commodity = commodityMapper.findById(commodityId);
        if (commodity == null) throw new BusinessException("无该商品！");
        CommodityStyle commodityStyle = commodityStyleMapper.findById(commodityStyleId);
        if (commodityStyle == null) throw new BusinessException("无该商品款式！");
        orderDetailMapper.insert(orderDetail);
    }


    /**
     * 更新订单详情信息
     *
     * @param orderDetail
     */
    public void update(OrderDetail orderDetail) {
        Integer orderId = orderDetail.getOrderId();
        Integer commodityId = orderDetail.getCommodityId();
        Integer commodityStyleId = orderDetail.getCommodityStyleId();

        Order order = orderMapper.findById(orderId);
        if (order == null)
            throw new BusinessException("无该订单！");
        Commodity commodity = commodityMapper.findById(commodityId);
        if (commodity == null)
            throw new BusinessException("无该商品！");
        CommodityStyle commodityStyle = commodityStyleMapper.findById(commodityStyleId);
        if (commodityStyle == null)
            throw new BusinessException("无该商品款式！");

        orderDetailMapper.update(orderDetail);
    }


    /**
     * 删除订单详情
     *
     * @param orderDetail
     */
    public void delete(OrderDetail orderDetail) {
        orderDetail.beforeDelete();
        orderDetailMapper.delete(orderDetail);
    }

    /**
     * 获取订单详情
     *
     * @param orderDetail
     * @return
     */
    public OrderDetail detail(OrderDetail orderDetail) {
        OrderDetail res = orderDetailMapper.findById(orderDetail.getId());
        if (res == null)
            throw new BusinessException("无该订单明细!");
        return res;
    }
}
