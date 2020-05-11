package com.webdrp.entity;

import com.webdrp.common.BaseBean;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by yuanming on 2018/8/15.
 */
public class Analysis extends BaseBean {

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("二级标题")
    private String subTitle;

    @ApiModelProperty("日期")
    private String date;

    @ApiModelProperty("时间")
    private String hour;

    @ApiModelProperty("数量")
    private Integer num;

    @ApiModelProperty("统计类型 0每天新用户统计")
    private Integer aType;

    @ApiModelProperty("金钱类统计，精度2位")
    private Double sumCount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getaType() {
        return aType;
    }

    public void setaType(Integer aType) {
        this.aType = aType;
    }

    public Double getSumCount() {
        return sumCount;
    }

    public void setSumCount(Double sumCount) {
        this.sumCount = sumCount;
    }
}
