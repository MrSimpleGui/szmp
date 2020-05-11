package com.webdrp.entity.vo;

import com.webdrp.entity.Like;

public class LikeVo extends Like{

    private String userName;

    private String nickName;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
