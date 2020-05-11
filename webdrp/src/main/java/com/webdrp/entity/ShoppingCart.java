package com.webdrp.entity;

import com.webdrp.common.BaseBean;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by yuanming on 2018/8/15.
 * 购物车
 * @author zym
 */
public class ShoppingCart  extends BaseBean{

    @ApiModelProperty("微信用户Id")
    private Integer richUserId;

    @ApiModelProperty("商品名称")
    private Integer commodityId;

    @ApiModelProperty("款式Id")
    private Integer commodityStyleId;

    @ApiModelProperty("短标题")
    private String name;

    @ApiModelProperty("长标题")
    private String nameItem;

    @ApiModelProperty("款式名称")
    private String commodityStyleName;

    @ApiModelProperty("款式金价格")
    private Double price;

    @ApiModelProperty("数量")
    private Integer inventory;

    @ApiModelProperty("图片链接，首页的商品图")
    private String imageUrl;

    public Integer getRichUserId() {
        return richUserId;
    }

    public void setRichUserId(Integer richUserId) {
        this.richUserId = richUserId;
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

    public String getCommodityStyleName() {
        return commodityStyleName;
    }

    public void setCommodityStyleName(String commodityStyleName) {
        this.commodityStyleName = commodityStyleName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
