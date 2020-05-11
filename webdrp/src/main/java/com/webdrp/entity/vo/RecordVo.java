package com.webdrp.entity.vo;

import com.webdrp.entity.Record;

public class RecordVo extends Record{

    private long totalPrice;

    private String nickname;

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
