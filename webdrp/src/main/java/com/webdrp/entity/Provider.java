package com.webdrp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.webdrp.common.BaseBean;
import com.webdrp.constant.AgentType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

@ApiModel("供应商用户表")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Provider extends BaseBean {

    @ApiModelProperty("供应商用户名")
    private String providerusername;

    @ApiModelProperty("供应商密码")
    @JsonIgnore
    private String providerpassword;
    @ApiModelProperty("必须 店铺名称 ")
    private String prividername;
    @ApiModelProperty("必须 店铺图片 200x200px 以下的图片")
    private String imageurl;
    @ApiModelProperty("必须 排序字段 默认传0")
    private Integer sort;
    @ApiModelProperty("富文本 店铺介绍")
    private String context;
    @ApiModelProperty(" 供应商状态0不可用 1可用")
    private Integer status;
    @ApiModelProperty("供应商地址")
    private String address;
    @ApiModelProperty("不填 供应商地址编码")
    private String addresscode;
    @ApiModelProperty("不填 供应商地址全地址")
    private String realaddress;
    @ApiModelProperty("填 供应商电话")
    private String phone;
    @ApiModelProperty("填 真实姓名")
    private String realName;

    @ApiModelProperty("代理商类型 0 普通代理商 1省级代理 2市级代理")
    private Integer agentType;

    @ApiModelProperty("省级代码 地址码")
    private String provinceCode;

    @ApiModelProperty("省名称")
    private String province;

    @ApiModelProperty("市级代码 地址码")
    private String cityCode;

    @ApiModelProperty("市名称")
    private String city;

    @ApiModelProperty("B端代理 身份证")
    private String idcard;
    //idcard,unionPay,unionPayDesc
    @ApiModelProperty("B端省市代理 银行卡号")
    private String unionPay;

    @ApiModelProperty("B端省市代理 银行卡银行")
    private String unionPayDesc;

    public String getProviderusername() {
        return providerusername;
    }

    public void setProviderusername(String providerusername) {
        this.providerusername = providerusername;
    }

    public String getProviderpassword() {
        return providerpassword;
    }

    public void setProviderpassword(String providerpassword) {
        this.providerpassword = providerpassword;
    }

    public String getPrividername() {
        return prividername;
    }

    public void setPrividername(String prividername) {
        this.prividername = prividername;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddresscode() {
        return addresscode;
    }

    public void setAddresscode(String addresscode) {
        this.addresscode = addresscode;
    }

    public String getRealaddress() {
        return realaddress;
    }

    public void setRealaddress(String realaddress) {
        this.realaddress = realaddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getAgentType() {
        return agentType;
    }

    public void setAgentType(Integer agentType) {
        this.agentType = agentType;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAgentTypeString(){
        if (Objects.isNull(getAgentType())){
            return "无此类型";
        }
        return AgentType.getString(getAgentType());
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getUnionPay() {
        return unionPay;
    }

    public void setUnionPay(String unionPay) {
        this.unionPay = unionPay;
    }

    public String getUnionPayDesc() {
        return unionPayDesc;
    }

    public void setUnionPayDesc(String unionPayDesc) {
        this.unionPayDesc = unionPayDesc;
    }
}
