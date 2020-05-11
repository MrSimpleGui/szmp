package com.webdrp.entity;

import com.webdrp.common.BaseBean;
import lombok.Data;

/**
 * Created by yuanming on 2018/8/13.
 * 商品特殊参数 ID1为报单   ID2为商城正常商品
 */
@Data
public class CommodityType extends BaseBean{

    //说明
    private String name;

    //层数
    private Integer grade;


}
