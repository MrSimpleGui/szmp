package com.webdrp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webdrp.common.BaseBean;
import io.swagger.models.auth.In;
import lombok.Data;

public class User extends BaseBean {

    //用户名
    private String username;
    //密码
    @JsonIgnore
    private String password;
    //密码
    private String nickName;
    //性别
    private Integer sex;
    //职位
    private String position;

    //状态
    private Integer status;
    //roleId
    private Integer roleId;
    //头像
    private String headImg;


    //构造函数
    public User() {

    }

    public User(String username, String password ){
        this.username = username;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

}

