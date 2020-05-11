package com.webdrp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webdrp.common.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("供应商收入模型")
public class AgentIncome extends BaseBean {

    @ApiModelProperty("分润标题")
    private String title;

    @ApiModelProperty("商品分润类型   1旅游线路分润 2.报单产品  3特产商城")
    private Integer agentIncomeType;

    @ApiModelProperty("商品ID，旅游线路ID")
    private Integer cid;

    @ApiModelProperty("款式ID,旅游线路的档期日期ID")
    private Integer csid;

    @ApiModelProperty("供应商ID")
    private Integer agentId;

    @ApiModelProperty("分润多少钱")
    private Double money;

    @ApiModelProperty("分润说明")
    private String description;

    @ApiModelProperty("分润日期")
    private String incomeDate;

    @ApiModelProperty("操着者ID  添加不填")
    @JsonIgnore
    private Integer userId;

    @ApiModelProperty("操着者账号  添加不填")
    @JsonIgnore
    private String userName;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAgentIncomeType() {
        return agentIncomeType;
    }

    public void setAgentIncomeType(Integer agentIncomeType) {
        this.agentIncomeType = agentIncomeType;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getCsid() {
        return csid;
    }

    public void setCsid(Integer csid) {
        this.csid = csid;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(String incomeDate) {
        this.incomeDate = incomeDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
