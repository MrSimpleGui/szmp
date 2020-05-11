package com.webdrp.entity;

import com.webdrp.common.BaseBean;

public class Spot extends BaseBean {
    //用户ID
    private Integer richuserId;
    //身份证号码
    private String idcard;
    //身份证名字
    private String idcardName;
    //旅游卡号码
    private String cardNum;
    //性别1男2女
    private Integer sex;
    //电话号码
    private String tel;
    //微信用户openId  可以为空
    private String openId;
    //昵称
    private String nickName;

    public Integer getRichuserId() {
        return richuserId;
    }

    public void setRichuserId(Integer richuserId) {
        this.richuserId = richuserId;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIdcardName() {
        return idcardName;
    }

    public void setIdcardName(String idcardName) {
        this.idcardName = idcardName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


}
