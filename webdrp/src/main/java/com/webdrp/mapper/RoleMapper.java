package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by yuanming on 2018/8/8.
 */
@Component
public interface RoleMapper extends BaseMapper<Role>{
    //单个添加权限
    void addPermissionToRole(@Param("roleId")Integer roleId,@Param("permissionId")Integer permissionId);
    //单个删除权限
    void removePermissionFromRole(@Param("roleId")Integer roleId,@Param("permissionId")Integer permissionId);
}
