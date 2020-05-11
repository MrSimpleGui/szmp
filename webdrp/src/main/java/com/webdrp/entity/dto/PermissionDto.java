package com.webdrp.entity.dto;

import com.webdrp.entity.Permission;

public class PermissionDto extends Permission {

    private Integer roleId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
