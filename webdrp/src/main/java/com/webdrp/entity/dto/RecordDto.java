package com.webdrp.entity.dto;

import com.webdrp.entity.Record;

public class RecordDto extends Record {


    private String startTime;//用于查询

    private String endTime;//

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