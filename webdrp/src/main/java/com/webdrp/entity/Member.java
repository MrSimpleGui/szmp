package com.webdrp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.webdrp.common.BaseBean;
import com.webdrp.constant.RichUserStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/*
*
*/
@ApiModel("用户表（微信|APP）")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Member extends BaseBean implements Serializable
{
    //直推id
    @ApiModelProperty("直推Id")
    private Integer zid;
    //见点id
    @ApiModelProperty("见点Id")
    private Integer jid;
    //用户名
    @ApiModelProperty("用户名，登录使用")
    private String username;
    //密码
    @JsonIgnore
    private String password;
    //状态0为可用1为封号
    @ApiModelProperty("0为可用1为封号，黑名单人物")
    private Integer status;
    //昵称
    @ApiModelProperty("昵称")
    private String nickname;
    //支付宝账号
    @ApiModelProperty("支付宝账号")
    private String zfb;
    //支付宝账号
    @ApiModelProperty("支付宝名字")
    private String zfbname;
    //钱包
    @ApiModelProperty("钱包")
    private Double wallet;
    //微信头像
    @ApiModelProperty("微信头像")
    private String headImg;
    //微信openid

    @ApiModelProperty("支付使用的 openid")
    private String openid;
    //微信openid公众号标识
    @ApiModelProperty("微信openid公众号标识")
    private String openidwx;
    //微信openid1
    @ApiModelProperty("授权使用 openid")
    private String openid1;
    //微信open1id公众号标识
    @ApiModelProperty("微信openid1公众号标识")
    private String openid1wx;
    //等级0 一级1 二级 2 3级
    @ApiModelProperty("等级0 一级1 二级 2 3级")
    private Integer grade;
    //是否关注
    @ApiModelProperty("是否关注0未关注，1为关注")
    private Integer IsSubscribe;
    //性别
    @ApiModelProperty("性别")
    private Integer sex;
    //城市
    @ApiModelProperty("城市")
    private String city;
    //国家
    @ApiModelProperty("国家")
    private String country;
    //省份
    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("复购次数")
    private Integer repurchase;

    @ApiModelProperty("电话号码,唯一")
    private String phone;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("默认收货地址")
    private String address;

    @ApiModelProperty("城市ID")
    private Integer cityId;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("开放平台ID")
    private String unionId;



    //构造函数
    public Member()
    {

    }

    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /// <summary>
    /// 获取直推id
    /// </summary>
    public Integer getZid()
    {
        return this.zid;
    }
    /// <summary>
    /// 设置直推id
    /// </summary>
    public void setZid(Integer zid)
    {
        this.zid = zid;
    }
    /// <summary>
    /// 获取见点id
    /// </summary>
    public Integer getJid()
    {
        return this.jid;
    }
    /// <summary>
    /// 设置见点id
    /// </summary>
    public void setJid(Integer jid)
    {
        this.jid = jid;
    }
    /// <summary>
    /// 获取用户名
    /// </summary>
    public String getUsername()
    {
        return this.username;
    }
    /// <summary>
    /// 设置用户名
    /// </summary>
    public void setUsername(String username)
    {
        this.username = username;
    }
    /// <summary>
    /// 获取密码
    /// </summary>
    public String getPassword()
    {
        return this.password;
    }
    /// <summary>
    /// 设置密码
    /// </summary>
    public void setPassword(String password)
    {
        this.password = password;
    }
    /// <summary>
    /// 获取状态0为可用1为封号
    /// </summary>
    public Integer getStatus()
    {
        return this.status;
    }
    /// <summary>
    /// 设置状态0为可用1为封号
    /// </summary>
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    /// <summary>
    /// 获取昵称
    /// </summary>
    public String getNickname()
    {
        return this.nickname;
    }
    /// <summary>
    /// 设置昵称
    /// </summary>
    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }
    /// <summary>
    /// 获取钱包
    /// </summary>
    public Double getWallet()
    {
        return this.wallet;
    }
    /// <summary>
    /// 设置钱包
    /// </summary>
    public void setWallet(Double wallet)
    {
        this.wallet = wallet;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getZfb() {
        return zfb;
    }

    public void setZfb(String zfb) {
        this.zfb = zfb;
    }

    public String getZfbname() {
        return zfbname;
    }

    public void setZfbname(String zfbname) {
        this.zfbname = zfbname;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getOpenidwx() {
        return openidwx;
    }

    public void setOpenidwx(String openidwx) {
        this.openidwx = openidwx;
    }

    public String getOpenid1() {
        return openid1;
    }

    public void setOpenid1(String openid1) {
        this.openid1 = openid1;
    }

    public String getOpenid1wx() {
        return openid1wx;
    }

    public void setOpenid1wx(String openid1wx) {
        this.openid1wx = openid1wx;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getIsSubscribe() {
        return IsSubscribe;
    }

    public void setIsSubscribe(Integer isSubscribe) {
        IsSubscribe = isSubscribe;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setRepurchase(Integer repurchase) {
        this.repurchase = repurchase;
    }

    public String getStatusString(){
        return RichUserStatus.getString(getStatus());
    }

    public Integer getRepurchase() {
        return repurchase;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    @Override
    public String toString() {
        return "RichUser{" +
                "zid=" + zid +
                ", jid=" + jid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", nickname='" + nickname + '\'' +
                ", zfb='" + zfb + '\'' +
                ", zfbname='" + zfbname + '\'' +
                ", wallet=" + wallet +
                ", headImg='" + headImg + '\'' +
                ", openid='" + openid + '\'' +
                ", openidwx='" + openidwx + '\'' +
                ", openid1='" + openid1 + '\'' +
                ", openid1wx='" + openid1wx + '\'' +
                ", grade=" + grade +
                ", IsSubscribe=" + IsSubscribe +
                ", sex=" + sex +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", repurchase=" + repurchase +
                ", phone='" + phone + '\'' +
                ", realName='" + realName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

