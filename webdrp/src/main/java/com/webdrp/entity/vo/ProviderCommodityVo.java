package com.webdrp.entity.vo;

import com.webdrp.entity.Commodity;
import com.webdrp.entity.Provider;

import java.util.List;

public class ProviderCommodityVo extends Provider {
    private List<Commodity> productList;

    public List<Commodity> getProductList() {
        return productList;
    }

    public void setProductList(List<Commodity> productList) {
        this.productList = productList;
    }
}
