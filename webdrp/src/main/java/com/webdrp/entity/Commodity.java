package com.webdrp.entity;

import com.webdrp.common.BaseBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by yuanming on 2018/8/10.
 */
@Data
public class Commodity extends BaseBean{

    @ApiModelProperty("供应商ID，保留字段")
    private Integer ventorId;

    @ApiModelProperty("供应商名称，保留字段")
    private String ventorName;

    @ApiModelProperty("商品一级标题")
    private String name;

    @ApiModelProperty("商品二级标题")
    private String nameItem;

    @ApiModelProperty("商品详细说明")
    private String context;

    @ApiModelProperty("是否发布1发布  0未发布")
    private Integer publish;

    @ApiModelProperty("0为普通产品（正常销售，不带任何返利）  1为报单产品（享受直推）  2 报单加建点（享受直推加建点） 3 三级分销商品")
    private Integer cType;

    @ApiModelProperty("返利价格")
    private Double rebate;

    @ApiModelProperty("建点价格")
    private Double jiandian;

    @ApiModelProperty("商品类别比如衣服，鞋子，日常，什么j8的，归类到CommodityClass那边去")
    private Integer classId;

    @ApiModelProperty("排序专用，默认值是0  吊大的在前")
    private Integer sort;

    @ApiModelProperty("商家版块的东西，备用默认值是1")
    private Integer review;

    @ApiModelProperty("首页图片 （新加）8/22")
    private String imgUrl;

    @ApiModelProperty("邮费0.00")
    private Double express;

    @ApiModelProperty("要分享的标题")
    private String shareTitle;

    @ApiModelProperty("要分享的图片")
    private String shareImg;

    @ApiModelProperty("分享海报")
    private String sharePoster;

    @ApiModelProperty("服务文本")
    private String service;

    @ApiModelProperty("参数规格")
    private String specification;


    public Integer getVentorId() {
        return ventorId;
    }

    public void setVentorId(Integer ventorId) {
        this.ventorId = ventorId;
    }

    public String getVentorName() {
        return ventorName;
    }

    public void setVentorName(String ventorName) {
        this.ventorName = ventorName;
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

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Integer getPublish() {
        return publish;
    }

    public void setPublish(Integer publish) {
        this.publish = publish;
    }

    public Integer getcType() {
        return cType;
    }

    public void setcType(Integer cType) {
        this.cType = cType;
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

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getReview() {
        return review;
    }

    public void setReview(Integer review) {
        this.review = review;
    }

    public Double getExpress() {
        return express;
    }

    public void setExpress(Double express) {
        this.express = express;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareImg() {
        return shareImg;
    }

    public void setShareImg(String shareImg) {
        this.shareImg = shareImg;
    }

    public String getSharePoster() {
        return sharePoster;
    }

    public void setSharePoster(String sharePoster) {
        this.sharePoster = sharePoster;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    @Override
    public String toString() {
        return "Commodity{" +
                "ventorId=" + ventorId +
                ", ventorName='" + ventorName + '\'' +
                ", name='" + name + '\'' +
                ", nameItem='" + nameItem + '\'' +
                ", context='" + context + '\'' +
                ", publish=" + publish +
                ", cType=" + cType +
                ", rebate=" + rebate +
                ", jiandian=" + jiandian +
                ", classId=" + classId +
                '}';
    }


    public static final class CommodityBuilder {
        private Integer ventorId;
        //主键
        private Integer id;
        //创建时间
        private String createTime;
        private String ventorName;
        //更新时间
        private String updateTime;
        private String name;
        //删除时间
        private String deleteTime;
        private String nameItem;
        private String context;
        private Integer publish;
        private Integer cType;
        private Double rebate;
        private Double jiandian;
        private Integer classId;
        private Double express;
        private String shareTitle;
        private String shareImg;
        private String sharePoster;
        private String service;
        private String specification;


        private CommodityBuilder() {
        }

        public static CommodityBuilder aCommodity() {
            return new CommodityBuilder();
        }

        public CommodityBuilder withVentorId(Integer ventorId) {
            this.ventorId = ventorId;
            return this;
        }

        public CommodityBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public CommodityBuilder withCreateTime(String createTime) {
            this.createTime = createTime;
            return this;
        }

        public CommodityBuilder withVentorName(String ventorName) {
            this.ventorName = ventorName;
            return this;
        }

        public CommodityBuilder withUpdateTime(String updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public CommodityBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CommodityBuilder withDeleteTime(String deleteTime) {
            this.deleteTime = deleteTime;
            return this;
        }

        public CommodityBuilder withNameItem(String nameItem) {
            this.nameItem = nameItem;
            return this;
        }

        public CommodityBuilder withContext(String context) {
            this.context = context;
            return this;
        }

        public CommodityBuilder withPublish(Integer publish) {
            this.publish = publish;
            return this;
        }

        public CommodityBuilder withCType(Integer cType) {
            this.cType = cType;
            return this;
        }

        public CommodityBuilder withRebate(Double rebate) {
            this.rebate = rebate;
            return this;
        }

        public CommodityBuilder withJiandian(Double jiandian) {
            this.jiandian = jiandian;
            return this;
        }

        public CommodityBuilder withClassId(Integer classId) {
            this.classId = classId;
            return this;
        }

        public CommodityBuilder withExpress(Double express) {
            this.express = express;
            return this;
        }

        public CommodityBuilder withShareTitle(String shareTitle) {
            this.shareTitle = shareTitle;
            return this;
        }

        public CommodityBuilder withShareImg(String shareImg) {
            this.shareImg = shareImg;
            return this;
        }

        public CommodityBuilder withSharePoster(String sharePoster) {
            this.sharePoster = sharePoster;
            return this;
        }

        public CommodityBuilder withService(String service) {
            this.service = service;
            return this;
        }

        public CommodityBuilder withSpecification(String specification) {
            this.specification = specification;
            return this;
        }


        public Commodity build() {
            Commodity commodity = new Commodity();
            commodity.setId(id);
            commodity.setCreateTime(createTime);
            commodity.setUpdateTime(updateTime);
            commodity.setDeleteTime(deleteTime);
            commodity.name = this.name;
            commodity.ventorName = this.ventorName;
            commodity.publish = this.publish;
            commodity.ventorId = this.ventorId;
            commodity.jiandian = this.jiandian;
            commodity.cType = this.cType;
            commodity.classId = this.classId;
            commodity.rebate = this.rebate;
            commodity.context = this.context;
            commodity.nameItem = this.nameItem;
            commodity.express = this.express;
            commodity.shareTitle = this.shareTitle;
            commodity.shareImg = this.shareImg;
            commodity.sharePoster = this.sharePoster;
            commodity.service = this.service;
            commodity.specification = this.specification;
            return commodity;
        }
    }

}
