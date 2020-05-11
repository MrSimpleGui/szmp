package com.webdrp.entity.dto;

import com.webdrp.entity.CommodityStyle;
import com.webdrp.entity.Image;

import java.util.List;

public class CommodityStyleDto extends CommodityStyle{

    private List<Image> images;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
