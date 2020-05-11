package com.webdrp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.webdrp.common.BaseBean;
import com.webdrp.constant.ApplicationStatus;
import com.webdrp.constant.ApplicationType;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Application extends BaseBean {

    @ApiModelProperty("真实名字")
    private String realName;

    @ApiModelProperty("电话")
    private String tel;

    @ApiModelProperty("商品款式Id")
    private Integer csid;

    @ApiModelProperty("性别  0男1女")
    private Integer sex;

    @ApiModelProperty("微信号")
    private String wechat;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("报名人数")
    private Integer signNum;

    @ApiModelProperty("是否有港澳通行证 0 无  1 有")
    private Integer hasCard;

    @ApiModelProperty("套餐名称")
    private String  csname;

    @ApiModelProperty("微信用户Id")
    private Integer richuserId;

    @ApiModelProperty("备注")
    private String note;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("申请类型")
    private Integer applicationType;

    @ApiModelProperty("申请状态")
    private Integer applicationStatus;

    @ApiModelProperty("身份证地址")
    private Integer idAddress;

    @ApiModelProperty("身份证号码")
    private Integer idcard;

    @ApiModelProperty("省级代码 地址码")
    private String provinceCode;


    @ApiModelProperty("省名称")
    private String province;

    @ApiModelProperty("市级代码 地址码")
    private String cityCode;


    @ApiModelProperty("市名称")
    private String city;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getCsid() {
        return csid;
    }

    public void setCsid(Integer csid) {
        this.csid = csid;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSignNum() {
        return signNum;
    }

    public void setSignNum(Integer signNum) {
        this.signNum = signNum;
    }

    public Integer getHasCard() {
        return hasCard;
    }

    public void setHasCard(Integer hasCard) {
        this.hasCard = hasCard;
    }

    public String getCsname() {
        return csname;
    }

    public void setCsname(String csname) {
        this.csname = csname;
    }

    public Integer getRichuserId() {
        return richuserId;
    }

    public void setRichuserId(Integer richuserId) {
        this.richuserId = richuserId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(Integer applicationType) {
        this.applicationType = applicationType;
    }

    public String getApplicationTypeString() {
        return ApplicationType.getCommodityToTypeString(getApplicationType());
    }
    //获取性别
    public String getSexString(){
        return getSex()==1?"女":"男";
    }

    public Integer getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(Integer applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getApplicationStatusString() {
        return ApplicationStatus.getString(getApplicationStatus());
    }

    public Integer getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(Integer idAddress) {
        this.idAddress = idAddress;
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

    public Integer getIdcard() {
        return idcard;
    }

    public void setIdcard(Integer idcard) {
        this.idcard = idcard;
    }
}


