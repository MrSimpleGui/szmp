package com.webdrp.entity.dto;

public class LogisticsData {

    private String time;

    private String status;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LogisticsData{" +
                "time='" + time + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
