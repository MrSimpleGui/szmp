package com.webdrp.entity.vo;

import com.webdrp.entity.Role;
import com.webdrp.entity.User;

/**
 * Created by yuanming on 2018/8/15.
 * 前端显示层
 */
public class UserVo extends User {

    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
