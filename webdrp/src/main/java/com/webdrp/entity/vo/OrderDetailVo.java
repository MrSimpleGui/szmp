package com.webdrp.entity.vo;

import com.webdrp.entity.OrderDetail;
import org.aspectj.weaver.ast.Or;

public class OrderDetailVo extends OrderDetail {

    private String commodityName;

    private String commodityStyleName;

    private String orderName;

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityStyleName() {
        return commodityStyleName;
    }

    public void setCommodityStyleName(String commodityStyleName) {
        this.commodityStyleName = commodityStyleName;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
}
