package com.webdrp.service.impl;

import com.webdrp.common.BaseServiceImpl;
import com.webdrp.entity.Permission;
import com.webdrp.entity.dto.PermissionDto;
import com.webdrp.mapper.PermissionMapper;
import com.webdrp.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission,PermissionMapper> implements PermissionService {


    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public List<Permission> findPermissionByRoleId(Integer roleId) {
        PermissionDto permissionDto = new PermissionDto();
        permissionDto.setRoleId(roleId);
        return permissionMapper.findPermissions(permissionDto);
    }

    @Override
    public List<Permission> findPermissions(PermissionDto permissionDto) {
        return permissionMapper.findPermissions(permissionDto);
    }

    @Override
    public List<Permission> findNotHavePermissionByRoleId(Integer id) {
        return permissionMapper.findNotHavePermissionByRoleId(id);
    }
}
