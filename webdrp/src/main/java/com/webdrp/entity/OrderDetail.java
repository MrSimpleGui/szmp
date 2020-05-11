package com.webdrp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.webdrp.common.BaseBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by yuanming on 2018/8/10.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class OrderDetail extends BaseBean{

    @ApiModelProperty("订单Id")
    private Integer orderId;

    @ApiModelProperty("商品id")
    private Integer commodityId;

    @ApiModelProperty("商品款式id")
    private Integer commodityStyleId;

    @ApiModelProperty("图片链接 一张")
    private String imageUrl;

    @ApiModelProperty("数量")
    private Integer count;

    @ApiModelProperty("一级标题")
    private String name;

    @ApiModelProperty("二级标题")
    private String nameItem;

    @ApiModelProperty("总价格  price*count = subPrice")
    private Double subPrice;

    @ApiModelProperty("微信用户openid")
    private String openId;

    @ApiModelProperty("微信用户id")
    private Integer richUserId;

    @ApiModelProperty("微信用户昵称")
    private String nickName;

    @ApiModelProperty("款式价格")
    private Double price;


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public Integer getCommodityStyleId() {
        return commodityStyleId;
    }

    public void setCommodityStyleId(Integer commodityStyleId) {
        this.commodityStyleId = commodityStyleId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    public Double getSubPrice() {
        return subPrice;
    }

    public void setSubPrice(Double subPrice) {
        this.subPrice = subPrice;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getRichUserId() {
        return richUserId;
    }

    public void setRichUserId(Integer richUserId) {
        this.richUserId = richUserId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
