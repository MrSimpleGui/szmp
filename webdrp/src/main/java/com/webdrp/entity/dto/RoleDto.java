package com.webdrp.entity.dto;

import com.webdrp.entity.Role;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class RoleDto extends Role{

    @ApiModelProperty("权限ID")
    private List<Integer> permissionIdList;

    public List<Integer> getPermissionIdList() {
        return permissionIdList;
    }

    public void setPermissionIdList(List<Integer> permissionIdList) {
        this.permissionIdList = permissionIdList;
    }
}
