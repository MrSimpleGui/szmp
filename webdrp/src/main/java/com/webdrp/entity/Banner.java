package com.webdrp.entity;

import com.webdrp.common.BaseBean;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: zhang yuan ming
 * @Date: create in 18:04 2020-03-16
 * @mail: zh878998515@gmail.com
 * @Description:轮播图
 */
public class Banner extends BaseBean {

    @ApiModelProperty("图片路径")
    private String url;

    @ApiModelProperty("优先级 0～999（排序）")
    private Integer priority;

    @ApiModelProperty("类型 0轮播图 1活动")
    private Integer type;

    @ApiModelProperty("跳转类型  0无跳转  1商品  2通知  3公司简介")
    private Integer useIn;

    @ApiModelProperty("附加参数")
    private String extra;

    @ApiModelProperty("菜单id 默认传0")
    private Integer menuId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUseIn() {
        return useIn;
    }

    public void setUseIn(Integer useIn) {
        this.useIn = useIn;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

}
