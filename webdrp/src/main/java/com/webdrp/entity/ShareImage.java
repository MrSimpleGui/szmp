package com.webdrp.entity;

import com.webdrp.common.BaseBean;

/**
 * @Author: zhang yuan ming
 * @Date: create in 17:43 2020-03-23
 * @mail: zh878998515@gmail.com
 * @Description:商品分享二维码
 */
public class ShareImage extends BaseBean {

    private Integer memberId;

    private String imageUrl;

    private Integer commodityId;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }
}
