package com.webdrp.entity;

import com.webdrp.common.BaseBean;

public class Logistics  extends BaseBean {

    private String number;

    private int orderId;

    private int richUserId;

    private String result;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getRichUserId() {
        return richUserId;
    }

    public void setRichUserId(int richUserId) {
        this.richUserId = richUserId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
