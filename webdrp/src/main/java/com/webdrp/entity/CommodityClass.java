package com.webdrp.entity;

import com.webdrp.common.BaseBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by yuanming on 2018/8/13.
 * 商品类型
 */
@Data
public class CommodityClass extends BaseBean{

    @ApiModelProperty("上级Id")
    private Integer pid;

    @ApiModelProperty("类别名称")
    private String name;

}
