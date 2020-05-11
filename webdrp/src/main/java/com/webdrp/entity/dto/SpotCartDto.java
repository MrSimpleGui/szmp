package com.webdrp.entity.dto;
//绑定旅游卡po类
public class SpotCartDto {
    //
    private String mobile;
    private String passport_num;
    private String  passport_code;
    private String real_name;
    private String id_number;
    private int gender;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassport_num() {
        return passport_num;
    }

    public void setPassport_num(String passport_num) {
        this.passport_num = passport_num;
    }

    public String getPassport_code() {
        return passport_code;
    }

    public void setPassport_code(String passport_code) {
        this.passport_code = passport_code;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
