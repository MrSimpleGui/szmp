package com.webdrp.controller.sys;

import com.webdrp.annotation.SysUserAnnotation;
import com.webdrp.common.BaseController;
import com.webdrp.common.BaseService;
import com.webdrp.common.Result;
import com.webdrp.entity.Permission;
import com.webdrp.entity.Role;
import com.webdrp.entity.User;
import com.webdrp.entity.dto.RoleDto;
import com.webdrp.service.PermissionService;
import com.webdrp.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by yuanming on 2018/8/8.
 */
@Api(tags = "后台接口角色相关")
@RestController
@RequestMapping("/sys/role")
public class RoleController  extends BaseController<Role,Integer> {

    @Autowired
    RoleService roleService;

    @Autowired
    PermissionService permissionService;

    /**
     * 抽象业务类  T 实体对象   ID为
     * @return
     */
    @Override
    public BaseService<Role> getService() {
        return roleService;
    }

    @ApiOperation("为角色添加权限")
    @PostMapping("/addPermissionToRole")
    public Result addPermissionToRole(@RequestHeader(value="token") String token, @SysUserAnnotation User user,RoleDto roleDto){
        roleService.addPermissionToRole(roleDto);
        return Result.success();
    }

    @ApiOperation("为角色删除权限")
    @PostMapping("/removePermissionFromRole")
    public Result removePermissionFromRole(@RequestHeader(value="token") String token, @SysUserAnnotation User user,RoleDto roleDto){
        roleService.removePermissionToRole(roleDto);
        return Result.success();
    }

    @ApiOperation("获取角色的权限列表")
    @GetMapping("/getRolePermissionList")
    public Result getRolePermissionList(@RequestHeader(value="token") String token,Role role){
        return Result.success().addAttribute("permissionList",permissionService.findPermissionByRoleId(role.getId()));
    }

    @ApiOperation("获取角色没有的权限列表")
    @GetMapping("/getRoleNotHavePermissionList")
    public Result getRoleNotHavePermissionList(@RequestHeader(value="token") String token,Role role){
        return Result.success().addAttribute("permissionList",permissionService.findNotHavePermissionByRoleId(role.getId()));
    }
}
