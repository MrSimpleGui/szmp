package com.webdrp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.webdrp.common.BaseBean;
import io.swagger.annotations.ApiModel;

/**
 * Created by yuanming on 2018/8/22.
 * 用户地址
 */
@ApiModel("用户表地址表（微信|APP）")
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserAddress extends BaseBean{

    private String address;

    private Integer addressType;

    private String note ;

    private String phone;

    private String name;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAddressType() {
        return addressType;
    }

    public void setAddressType(Integer addressType) {
        this.addressType = addressType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
