package com.webdrp.entity;

import com.webdrp.common.BaseBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Propcard extends BaseBean {

    @ApiModelProperty("用户id")
    private Integer richUserId;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品id")
    private Integer commodityId;

    @ApiModelProperty("商品款式id")
    private Integer commodityStyleId;

    @ApiModelProperty("数量")
    private Integer count;

    @ApiModelProperty("0改名卡 1改级卡")
    private Integer type;
}
