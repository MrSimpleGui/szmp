package com.webdrp.entity.dto;

import java.util.List;

public class Result {

    private String number;

    private String type;

    private List<LogisticsData> list;

    private String deliverystatus;

    private String issign;

    private String expName;

    private String expSite;

    private String expPhone;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<LogisticsData> getList() {
        return list;
    }

    public void setList(List<LogisticsData> list) {
        this.list = list;
    }

    public String getDeliverystatus() {
        return deliverystatus;
    }

    public void setDeliverystatus(String deliverystatus) {
        this.deliverystatus = deliverystatus;
    }

    public String getIssign() {
        return issign;
    }

    public void setIssign(String issign) {
        this.issign = issign;
    }

    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public String getExpSite() {
        return expSite;
    }

    public void setExpSite(String expSite) {
        this.expSite = expSite;
    }

    public String getExpPhone() {
        return expPhone;
    }

    public void setExpPhone(String expPhone) {
        this.expPhone = expPhone;
    }

    @Override
    public String toString() {
        return "Result{" +
                "number='" + number + '\'' +
                ", type='" + type + '\'' +
                ", list=" + list +
                ", deliverystatus='" + deliverystatus + '\'' +
                ", issign='" + issign + '\'' +
                ", expName='" + expName + '\'' +
                ", expSite='" + expSite + '\'' +
                ", expPhone='" + expPhone + '\'' +
                '}';
    }
}
