package com.webdrp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webdrp.common.BaseBean;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Author: zhang yuan ming
 * @Date: create in 下午1:18 2018/2/18
 * @mail: zh878998515@gmail.com
 * @Description:
 */

public class Log extends BaseBean{


    @ApiModelProperty("日志类型")
    private String type;

    @ApiModelProperty("日志标题")
    private String title;

    @ApiModelProperty("请求地址")
    private String remoteAddr;

    @ApiModelProperty("URI")
    private String requestUri;

    @ApiModelProperty("请求方式")
    private String method;

    @ApiModelProperty("提交参数")
    private String params;

    @ApiModelProperty("异常")
    private String exception;

    @ApiModelProperty("开始时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date operateDate;

    @ApiModelProperty("请求时间毫秒")
    private String timeout;

    @ApiModelProperty("用户ID")
    private Integer userId;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
