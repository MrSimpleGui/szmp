package com.webdrp.entity;

import com.webdrp.common.BaseBean;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: zhang yuan ming
 * @Date: create in 18:34 2020-04-23
 * @mail: zh878998515@gmail.com
 * @Description:
 */
public class Cardbaglog extends BaseBean {

    @ApiModelProperty("源用户ID")
    private Integer orignUserId;

    @ApiModelProperty("送给人的ID")
    private Integer toUserId;

    @ApiModelProperty("数量")
    private Integer number;

    @ApiModelProperty("备注")
    private String note;

    public Integer getOrignUserId() {
        return orignUserId;
    }

    public void setOrignUserId(Integer orignUserId) {
        this.orignUserId = orignUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}