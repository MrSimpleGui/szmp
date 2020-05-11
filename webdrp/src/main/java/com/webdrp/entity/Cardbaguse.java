package com.webdrp.entity;

import com.webdrp.common.BaseBean;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: zhang yuan ming
 * @Date: create in 18:37 2020-04-23
 * @mail: zh878998515@gmail.com
 * @Description:
 */
public class Cardbaguse extends BaseBean {

    @ApiModelProperty("微信用户ID")
    private Integer richUserId;

    @ApiModelProperty("用户开通会员的手机号")
    private String phone;

    @ApiModelProperty("用户开通会员的姓名")
    private String name;

    public Integer getRichUserId() {
        return richUserId;
    }

    public void setRichUserId(Integer richUserId) {
        this.richUserId = richUserId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}