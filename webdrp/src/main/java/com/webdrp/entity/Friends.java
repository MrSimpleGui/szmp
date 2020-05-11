package com.webdrp.entity;

import com.webdrp.common.BaseBean;

public class Friends extends BaseBean {

    private String message;//朋友圈内容

    private Integer richUserId;

    public Integer getRichUserId() {
        return richUserId;
    }

    public void setRichUserId(Integer richUserId) {
        this.richUserId = richUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
