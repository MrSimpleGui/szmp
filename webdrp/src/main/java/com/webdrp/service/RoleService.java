package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.entity.Role;
import com.webdrp.entity.dto.RoleDto;

/**
 * Created by yuanming on 2018/8/8.
 */
public interface RoleService extends BaseService<Role> {

    void addPermissionToRole(RoleDto roleDto);

    void removePermissionToRole(RoleDto roleDto);

}
