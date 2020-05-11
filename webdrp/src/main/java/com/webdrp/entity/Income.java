package com.webdrp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.webdrp.common.BaseBean;
import com.webdrp.constant.IncomeType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by yuanming on 2018/8/13.
 * 收入记录
 */
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Income extends BaseBean{

    @ApiModelProperty("订单ID")
    private Integer orderId;

    @ApiModelProperty("目标用户Id")
    private Integer targetRichUserId;

    @ApiModelProperty("起源用户ID")
    private Integer originRichUserId;

    @ApiModelProperty("备注")
    private String note;

    @ApiModelProperty("金额")
    private Double money;

    @ApiModelProperty("当前目标用户级别")
    private Integer grade;

    @ApiModelProperty("层级")
    private Integer floor;

    @ApiModelProperty("数量")
    private Integer amount;

    @ApiModelProperty("收入类型，直推，建点,感恩，分润，商品分销，见IncomeType")
    private Integer incomeType;

    public Income() {
    }

    public Income(Integer orderId, Integer grade, Integer incomeType) {
        this.orderId = orderId;
        this.grade = grade;
        this.incomeType = incomeType;
    }

    public Income(Integer orderId, Integer targetRichUserId, Integer originRichUserId, String note, Double money, Integer grade, Integer floor, Integer amount, Integer incomeType) {
        this.orderId = orderId;
        this.targetRichUserId = targetRichUserId;
        this.originRichUserId = originRichUserId;
        this.note = note;
        this.money = money;
        this.grade = grade;
        this.floor = floor;
        this.amount = amount;
        this.incomeType = incomeType;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getTargetRichUserId() {
        return targetRichUserId;
    }

    public void setTargetRichUserId(Integer targetRichUserId) {
        this.targetRichUserId = targetRichUserId;
    }

    public Integer getOriginRichUserId() {
        return originRichUserId;
    }

    public void setOriginRichUserId(Integer originRichUserId) {
        this.originRichUserId = originRichUserId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(Integer incomeType) {
        this.incomeType = incomeType;
    }

    public String getImcomeTypeString(){
        return IncomeType.getIncomeTypeString(getIncomeType());
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Income{" +
                "orderId=" + orderId +
                ", targetRichUserId=" + targetRichUserId +
                ", originRichUserId=" + originRichUserId +
                ", note='" + note + '\'' +
                ", money=" + money +
                ", grade=" + grade +
                ", floor=" + floor +
                ", amount=" + amount +
                ", incomeType=" + incomeType +
                '}';
    }
}
