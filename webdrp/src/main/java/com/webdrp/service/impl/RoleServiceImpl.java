package com.webdrp.service.impl;

import com.webdrp.common.BaseMapper;
import com.webdrp.common.BaseServiceImpl;
import com.webdrp.entity.Role;
import com.webdrp.entity.dto.RoleDto;
import com.webdrp.err.BusinessException;
import com.webdrp.mapper.RoleMapper;
import com.webdrp.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Created by yuanming on 2018/8/8.
 */
@Service
public class RoleServiceImpl  extends BaseServiceImpl<Role,RoleMapper> implements RoleService {

    @Autowired
    RoleMapper roleMapper;



    @Override
    public void add(Role role) {
        super.add(role);
    }


    @Override
    public void addPermissionToRole(RoleDto roleDto) {
        if (Objects.isNull(roleDto.getId())){
            throw new BusinessException("角色ID不能为空");
        }
        if (Objects.isNull(roleDto.getPermissionIdList())){
            throw new BusinessException("权限ID不能为空");
        }
        for(int i = 0; i < roleDto.getPermissionIdList().size();i++){
            roleMapper.addPermissionToRole(roleDto.getId(),roleDto.getPermissionIdList().get(i));
        }
    }

    @Override
    public void removePermissionToRole(RoleDto roleDto) {
        if (Objects.isNull(roleDto.getId())){
            throw new BusinessException("角色ID不能为空");
        }
        if (Objects.isNull(roleDto.getPermissionIdList())){
            throw new BusinessException("权限ID不能为空");
        }
        for(int i = 0; i < roleDto.getPermissionIdList().size();i++){
            roleMapper.removePermissionFromRole(roleDto.getId(),roleDto.getPermissionIdList().get(i));
        }
    }
}
