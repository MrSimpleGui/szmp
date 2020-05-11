package com.webdrp.entity;

import com.webdrp.common.BaseBean;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: zhang yuan ming
 * @Date: create in 23:06 2020-02-21
 * @mail: zh878998515@gmail.com
 * @Description:签到
 */
public class SignIn extends BaseBean {

    @ApiModelProperty("微信用户Id")
    private Integer userId;
    @ApiModelProperty("微信用户昵称")
    private String nickName;
    @ApiModelProperty("签到日期")
    private String date;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
