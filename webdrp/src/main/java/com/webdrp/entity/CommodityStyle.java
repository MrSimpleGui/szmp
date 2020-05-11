package com.webdrp.entity;

import com.webdrp.common.BaseBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by yuanming on 2018/8/10.
 * 商品款式表
 */
/*@Data*/
public class CommodityStyle extends BaseBean{

    @ApiModelProperty("商品Id")
    private Integer commodityId;

    @ApiModelProperty("商品款式名称")
    private String name;

    @ApiModelProperty("款式价格")
    private Double price;

    @ApiModelProperty("款式成本")
    private Double cost;

    @ApiModelProperty("市场价格（新加）8/22")
    private Double normalPrice;

    @ApiModelProperty("库存")
    private Integer stock;

    public Double getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(Double normalPrice) {
        this.normalPrice = normalPrice;
    }

    @Override
    public String toString() {
        return "CommodityStyle{" +
                "commodityId=" + commodityId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", cost=" + cost +
                ", stock=" + stock +
                '}';
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public static final class CommodityStyleBuilder {
        private Integer commodityId;
        //主键
        private Integer id;
        //创建时间
        private String createTime;
        private String name;
        //更新时间
        private String updateTime;
        private Double price;
        //删除时间
        private String deleteTime;
        private Double cost;
        private Integer stock;

        private CommodityStyleBuilder() {
        }

        public static CommodityStyleBuilder builder() {
            return new CommodityStyleBuilder();
        }

        public CommodityStyleBuilder commodityId(Integer commodityId) {
            this.commodityId = commodityId;
            return this;
        }

        public CommodityStyleBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public CommodityStyleBuilder createTime(String createTime) {
            this.createTime = createTime;
            return this;
        }

        public CommodityStyleBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CommodityStyleBuilder updateTime(String updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public CommodityStyleBuilder price(Double price) {
            this.price = price;
            return this;
        }

        public CommodityStyleBuilder deleteTime(String deleteTime) {
            this.deleteTime = deleteTime;
            return this;
        }

        public CommodityStyleBuilder cost(Double cost) {
            this.cost = cost;
            return this;
        }

        public CommodityStyleBuilder stock(Integer stock) {
            this.stock = stock;
            return this;
        }

        public CommodityStyle build() {
            CommodityStyle commodityStyle = new CommodityStyle();
            commodityStyle.setId(id);
            commodityStyle.setCreateTime(createTime);
            commodityStyle.setUpdateTime(updateTime);
            commodityStyle.setDeleteTime(deleteTime);
            commodityStyle.cost = this.cost;
            commodityStyle.commodityId = this.commodityId;
            commodityStyle.stock = this.stock;
            commodityStyle.name = this.name;
            commodityStyle.price = this.price;
            return commodityStyle;
        }
    }


}
