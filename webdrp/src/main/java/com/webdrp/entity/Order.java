package com.webdrp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.webdrp.common.BaseBean;
import com.webdrp.constant.OrderStatus;
import com.webdrp.constant.OrderType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by yuanming on 2018/8/10.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Order extends BaseBean{

    @ApiModelProperty("用户ID")
    private Integer richUserId;

    @ApiModelProperty("/微信openID")
    private String openId;

    @ApiModelProperty("/总价格")
    private Double subPrice;

    @ApiModelProperty("/订单状态 0未支付 1支付确认中 2已支付 3已取消 4支付确认失败")  //未支付  支付中  带
    private Integer status;

    @ApiModelProperty("订单一级标题")
    private String name;

    @ApiModelProperty("订单二级标题")
    private String nameItem;

    @ApiModelProperty("物流单号")
    private String wuliuId;

    @ApiModelProperty("微信支付0支付宝为1")
    private Integer type;

    @ApiModelProperty("支付宝订单Id")
    private String zfbOrderId;

    @ApiModelProperty("收获地址")
    private String address;

    @ApiModelProperty("电话号码")
    private String phone;

    @ApiModelProperty("收货人")
    private String takeName;

    @ApiModelProperty("微信订单号")
    private String wechatOrderId;

    @ApiModelProperty("订单类型0为不反利 1反利 2返利加建点")
    private Integer orderType;

    @ApiModelProperty("直推金额")
    private Double rebate;

    @ApiModelProperty("建点金额")
    private Double jiandian;

    @ApiModelProperty("用户层面看到的orderId,仅仅作为查单使用，不想给订单ID")
    private String userOrder;

     @ApiModelProperty("卡类型 1实名卡  0电子卡 ")
    private Integer cardType;


    public Integer getRichUserId() {
        return richUserId;
    }

    public void setRichUserId(Integer richUserId) {
        this.richUserId = richUserId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Double getSubPrice() {
        return subPrice;
    }

    public void setSubPrice(Double subPrice) {
        this.subPrice = subPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public String getWuliuId() {
        return wuliuId;
    }

    public void setWuliuId(String wuliuId) {
        this.wuliuId = wuliuId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getZfbOrderId() {
        return zfbOrderId;
    }

    public void setZfbOrderId(String zfbOrderId) {
        this.zfbOrderId = zfbOrderId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTakeName() {
        return takeName;
    }

    public void setTakeName(String takeName) {
        this.takeName = takeName;
    }

    public String getWechatOrderId() {
        return wechatOrderId;
    }

    public void setWechatOrderId(String wechatOrderId) {
        this.wechatOrderId = wechatOrderId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Double getRebate() {
        return rebate;
    }

    public void setRebate(Double rebate) {
        this.rebate = rebate;
    }

    public Double getJiandian() {
        return jiandian;
    }

    public void setJiandian(Double jiandian) {
        this.jiandian = jiandian;
    }

    public String getTypeString(){
        return OrderType.getString(getType());
    }

    public String getStatusString(){
        return OrderStatus.getString(getStatus());
    }

    public String getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(String userOrder) {
        this.userOrder = userOrder;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    @Override
    public String toString() {
        return "Order{" +
                "richUserId=" + richUserId +
                ", openId='" + openId + '\'' +
                ", subPrice=" + subPrice +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", nameItem='" + nameItem + '\'' +
                ", wuliuId='" + wuliuId + '\'' +
                ", type=" + type +
                ", zfbOrderId='" + zfbOrderId + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", takeName='" + takeName + '\'' +
                ", wechatOrderId='" + wechatOrderId + '\'' +
                ", orderType=" + orderType +
                ", rebate=" + rebate +
                ", jiandian=" + jiandian +
                ", userOrder='" + userOrder + '\'' +
                '}';
    }
}
