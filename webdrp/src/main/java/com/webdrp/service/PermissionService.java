package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.entity.Permission;
import com.webdrp.entity.dto.PermissionDto;

import java.util.List;

public interface PermissionService extends BaseService<Permission> {

    List<Permission> findPermissionByRoleId(Integer roleId);

    List<Permission> findPermissions(PermissionDto permissionDto);

    List<Permission> findNotHavePermissionByRoleId(Integer id);
}
