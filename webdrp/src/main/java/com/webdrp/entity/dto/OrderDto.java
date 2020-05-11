package com.webdrp.entity.dto;

import com.webdrp.entity.Order;

/**
 * Created by yuanming on 2018/8/23.
 */
public class OrderDto extends Order {

    private String startTime;//用于查询

    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
