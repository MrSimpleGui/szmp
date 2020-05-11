package com.webdrp.entity;

import com.webdrp.common.BaseBean;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: zhang yuan ming
 * @Date: create in 18:10 2020-04-23
 * @mail: zh878998515@gmail.com
 * @Description:
 */
public class CardBag extends BaseBean {

    @ApiModelProperty("用户ID")
    private Integer richUserId;

    @ApiModelProperty("数量")
    private Integer number;

    public Integer getRichUserId() {
        return richUserId;
    }

    public void setRichUserId(Integer richUserId) {
        this.richUserId = richUserId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}