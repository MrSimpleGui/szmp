package com.webdrp.entity;

import com.webdrp.common.BaseBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Menu extends BaseBean {

    @ApiModelProperty("父id")
    private Integer pid;

    @ApiModelProperty("菜单排序")
    private Integer sort;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("图片全路径链接")
    private String imgUrl;

    @ApiModelProperty("是否发布 0未发布 1发布")
    private Integer publish;

    @ApiModelProperty("菜单类型 0商品  1通知  2APP下载 3简介")
    private Integer type;
}
