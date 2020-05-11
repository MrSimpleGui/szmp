package com.webdrp.entity;

import com.webdrp.common.BaseBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MenuCommodity extends BaseBean {

    @ApiModelProperty("菜单id")
    private Integer menuId;

    @ApiModelProperty("商品id")
    private Integer commodityId;

    @ApiModelProperty("排序")
    private Integer sort;
}
