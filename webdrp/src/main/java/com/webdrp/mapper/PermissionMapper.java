package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.entity.Permission;
import com.webdrp.entity.dto.PermissionDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> findPermissions(PermissionDto permissionDto);

    List<Permission> findNotHavePermissionByRoleId(Integer id);
}
