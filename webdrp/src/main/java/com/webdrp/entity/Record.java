package com.webdrp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.webdrp.common.BaseBean;
import com.webdrp.constant.RecordStatus;
import com.webdrp.constant.RecordType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by yuanming on 2018/8/13.
 * 提现记录
 */
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Record extends BaseBean {

    @ApiModelProperty("目标用户Id")
    private Integer targetRichUserId;

    @ApiModelProperty("金额")
    private Double money;

    @ApiModelProperty("手续费")
    private Double poundage;

    @ApiModelProperty("支付宝用户名")
    private String zfbName;

    @ApiModelProperty("支付宝账号")
    private String zfb;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("提现状态")
    private Integer status;

    @ApiModelProperty("提现类型  微信提现，支付宝提现")
    private Integer rType;

    @ApiModelProperty("提现失败状态说明")
    public String note;

    @ApiModelProperty("提交的支付宝单号")
    public String zfbOrderNo;

    @ApiModelProperty("8.30 支付宝返回的，后台不需要显示")
    public String orderId;

    @ApiModelProperty("微信openID")
    public String openid;

    public Integer getTargetRichUserId() {
        return targetRichUserId;
    }

    public void setTargetRichUserId(Integer targetRichUserId) {
        this.targetRichUserId = targetRichUserId;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getZfbName() {
        return zfbName;
    }

    public void setZfbName(String zfbName) {
        this.zfbName = zfbName;
    }

    public String getZfb() {
        return zfb;
    }

    public void setZfb(String zfb) {
        this.zfb = zfb;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusString(){
        return RecordStatus.getString(getStatus());
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getZfbOrderNo() {
        return zfbOrderNo;
    }

    public void setZfbOrderNo(String zfbOrderNo) {
        this.zfbOrderNo = zfbOrderNo;
    }

    public Integer getrType() {
        return rType;
    }

    public void setrType(Integer rType) {
        this.rType = rType;
    }

    public String getrTypeString(){
        return RecordType.getString(getrType());
    }

    public Double getPoundage() {
        return poundage;
    }

    public void setPoundage(Double poundage) {
        this.poundage = poundage;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "Record{" +
                "targetRichUserId=" + targetRichUserId +
                ", money=" + money +
                ", poundage=" + poundage +
                ", zfbName='" + zfbName + '\'' +
                ", zfb='" + zfb + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                ", rType=" + rType +
                ", note='" + note + '\'' +
                ", zfbOrderNo='" + zfbOrderNo + '\'' +
                ", orderId='" + orderId + '\'' +
                ", openid='" + openid + '\'' +
                '}';
    }
}
