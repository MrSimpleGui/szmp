package com.webdrp.entity.vo;

import com.webdrp.entity.Order;
import com.webdrp.entity.OrderDetail;

import java.util.List;

/**
 * Created by yuanming on 2018/8/23.
 */
public class OrderVo extends Order {

    private List<OrderDetail> orderDetails;

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    private String nickName;

    private Integer richUserId;

    private Integer detailCount;

    private String detailName;

    private String detailNameItem;

    private Double detailSubPrice;

    public Integer getDetailCount() {
        return detailCount;
    }

    public void setDetailCount(Integer detailCount) {
        this.detailCount = detailCount;
    }

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    public String getDetailNameItem() {
        return detailNameItem;
    }

    public void setDetailNameItem(String detailNameItem) {
        this.detailNameItem = detailNameItem;
    }

    public Double getDetailSubPrice() {
        return detailSubPrice;
    }

    public void setDetailSubPrice(Double detailSubPrice) {
        this.detailSubPrice = detailSubPrice;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public Integer getRichUserId() {
        return richUserId;
    }

    @Override
    public void setRichUserId(Integer richUserId) {
        this.richUserId = richUserId;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
