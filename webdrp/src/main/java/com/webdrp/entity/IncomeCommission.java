package com.webdrp.entity;

import com.webdrp.common.BaseBean;


public class IncomeCommission extends BaseBean {
    //收入id
    private Integer incomeId;

    //第三方平台订单id
    private Integer orderId;

    //备注
    private String note;

    public Integer getIncomeId()
    {
        return this.incomeId;
    }

    public void setIncomeId(Integer incomeId)
    {
        this.incomeId = incomeId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
