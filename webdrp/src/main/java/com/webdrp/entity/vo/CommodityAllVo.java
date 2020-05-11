package com.webdrp.entity.vo;

import com.webdrp.entity.Commodity;
import com.webdrp.entity.Image;

import java.util.List;

/**
 * Created by yuanming on 2018/8/10.
 * 给微信端的产品
 */
public class CommodityAllVo extends Commodity{

    private List<Image> images;//图片

    private List<CommodityStyleVo> commodityStyleVoList;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<CommodityStyleVo> getCommodityStyleVoList() {
        return commodityStyleVoList;
    }

    public void setCommodityStyleVoList(List<CommodityStyleVo> commodityStyleVoList) {
        this.commodityStyleVoList = commodityStyleVoList;
    }
}
