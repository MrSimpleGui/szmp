package com.webdrp.entity.vo;

import com.webdrp.entity.CommodityStyle;
import com.webdrp.entity.Image;
import lombok.Data;

import java.util.List;

/**
 * Created by yuanming on 2018/8/10.
 */

public class CommodityStyleVo extends CommodityStyle {

    //商品名字
    private String commodityName;

    private List<Image> images;

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
