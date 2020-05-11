package com.webdrp.entity.vo;

import com.webdrp.entity.Commodity;

/**
 * Created by yuanming on 2018/8/10.
 * 给微信端的产品
 */
public class CommodityVoIndex extends Commodity{

    private Double price;

    private Double repurchasePrice;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getRepurchasePrice() {
        return repurchasePrice;
    }

    public void setRepurchasePrice(Double repurchasePrice) {
        this.repurchasePrice = repurchasePrice;
    }
}
