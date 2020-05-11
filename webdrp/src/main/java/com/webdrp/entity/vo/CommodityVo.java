package com.webdrp.entity.vo;

import com.webdrp.entity.Commodity;
import com.webdrp.entity.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanming on 2018/8/10.
 * 给微信端的产品
 */
public class CommodityVo extends Commodity{

    private List<Image> images;//图片

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
