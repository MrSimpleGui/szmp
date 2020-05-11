package com.webdrp.entity;

import com.webdrp.common.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("供应商提现")
public class AgentRecord extends BaseBean {

    @ApiModelProperty("提现")
    private String title;

    @ApiModelProperty("提现状态 0 提现中 1提现成功  2提现失败")
    private Integer status;

    @ApiModelProperty(value = "分润标题",required = true)
    private Double money;

    @ApiModelProperty(value = "供应商ID")
    private Integer agentId;

    @ApiModelProperty("供应商名称")
    private Integer agentName;

    @ApiModelProperty("供应商B端不显示 提现备注")
    private Integer note;

    @ApiModelProperty(value = "操着者ID")
    private String userId;

    @ApiModelProperty("操着者账号")
    private String userName;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getAgentName() {
        return agentName;
    }

    public void setAgentName(Integer agentName) {
        this.agentName = agentName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
