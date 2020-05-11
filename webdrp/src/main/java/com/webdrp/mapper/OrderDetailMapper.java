package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.entity.OrderDetail;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {


    void deleteByOrderId(OrderDetail orderDetail);

    List<OrderDetail> findByOrderId(Integer orderId);
}
