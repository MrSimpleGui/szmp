package com.webdrp.controller.sys;

import com.webdrp.annotation.SysUserAnnotation;
import com.webdrp.common.BaseController;
import com.webdrp.common.BaseService;
import com.webdrp.common.Result;
import com.webdrp.constant.PermissionType;
import com.webdrp.constant.PermissionWeight;
import com.webdrp.entity.Permission;
import com.webdrp.entity.User;
import com.webdrp.entity.dto.PermissionDto;
import com.webdrp.service.PermissionService;
import com.webdrp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api(tags = "后台接口系统端 权限相关")
@RestController
@RequestMapping("/sys/permission")
public class PermissionController extends BaseController<Permission,Integer> {


    @Autowired
    PermissionService permissionService;

    @Autowired
    UserService userService;

    /**
     * 抽象业务类  T 实体对象   ID为
     * @return
     */
    @Override
    public BaseService<Permission> getService() {
        return permissionService;
    }
    @ApiOperation("获取我的权限，set集合")
    @GetMapping("/getMyPermission")
    public Result getMyPermission(@RequestHeader(value="token") String token, @SysUserAnnotation User user){
       User user1 =  userService.findOne(user.getId());
        List<Permission> permissions = permissionService.findPermissionByRoleId(user1.getRoleId());
        Set<String> permissionSet = new HashSet<>();
        for(Permission permission : permissions){
            permissionSet.add(permission.getEsName());
        }
        return Result.success().addAttribute("permissions",permissionSet);
    }

    @ApiOperation("获取我的路由权限，set集合")
    @GetMapping("/getMyWebPagePermissions")
    public Result getMyWebPagePermissions(@RequestHeader(value="token") String token, @SysUserAnnotation User user){
        User user1 =  userService.findOne(user.getId());
        PermissionDto permissionDto = new PermissionDto();
        permissionDto.setPermissionType(PermissionType.WEB_PAGE);
        permissionDto.setRoleId(user1.getRoleId());
        List<Permission> permissions = permissionService.findPermissions(permissionDto);
        Set<Object> permissionSet = new HashSet<>();
        for(Permission permission : permissions){
            Map<String,Object> map = new HashMap<>();
            Set<String> emptySet = new HashSet<>();
            emptySet.add("不能作为判断key");
            emptySet.add("不能作为判断key1");
            map.put(permission.getEsName(),emptySet);
            //获取权限的子节点
            permissionDto.setPid(permission.getId());
            List<Permission> list = permissionService.findPermissions(permissionDto);
            if (Objects.nonNull(list)&&list.size()>0){
                Set<Object> childSet = new HashSet<>();
                for(Permission childPermission : list) {
                    Map<String, Object> childMap = new HashMap<>();
                    childMap.put(childPermission.getEsName(),emptySet);
                    childMap.put("children",null);
                    childSet.add(childMap);
                }
                map.put("children",childSet);
            }else{
                map.put("children",null);
            }
            permissionSet.add(map);
        }
        return Result.success().addAttribute("permissions",permissionSet);
    }


    @ApiOperation("获取我的按钮权限，set集合")
    @GetMapping("/getMyWebButtonPermissions")
    public Result getMyButtonPermissions(@RequestHeader(value="token") String token, @SysUserAnnotation User user){
        User user1 =  userService.findOne(user.getId());
        PermissionDto permissionDto = new PermissionDto();
        permissionDto.setPermissionType(PermissionType.WEB_PAGE_BUTTON);
        permissionDto.setRoleId(user1.getRoleId());
        List<Permission> permissions = permissionService.findPermissions(permissionDto);
        Set<String> permissionSet = new HashSet<>();
        for(Permission permission : permissions){
            permissionSet.add(permission.getEsName());
        }
        return Result.success().addAttribute("btnPermissions",permissionSet);
    }

    @GetMapping("/permissionWeight")
    @ApiOperation("权限权重常量集合")
    public Result getPermissionWeights(@RequestHeader(value="token") String token, @SysUserAnnotation User user){
        return Result.success().addAttribute("permissionWeights",PermissionWeight.map);
    }
    @ApiOperation("根据类型获取集合权限")
    @GetMapping("/getPermissionByType")
    public Result  getPermissionByType(@RequestHeader(value="token") String token,@RequestParam Integer permissionType){
        Permission permission = new Permission();
        permission.setPermissionType(permissionType);
        List<Permission>  list =  permissionService.findAll(permission);
        return Result.success().addAttribute("permissions",list);
    }

    @ApiOperation("分组获取权限集合")
    @GetMapping("/getPermissionGroupByType")
    public Result  getPermissionGroupByType(@RequestHeader(value="token") String token){
        Permission permission = new Permission();
        List<Permission>  list =  permissionService.findAll(permission);
        List<Permission> webPageUrl = new ArrayList<>();
        List<Permission> webButton = new ArrayList<>();
        List<Permission> backUrl = new ArrayList<>();
        for (Permission temp  : list){
            if (temp.getPermissionType().intValue()==PermissionType.BACK_URL.intValue()){
                backUrl.add(temp);
            }
            if (temp.getPermissionType().intValue()==PermissionType.WEB_PAGE.intValue()){
                webPageUrl.add(temp);
            }
            if (temp.getPermissionType().intValue()==PermissionType.WEB_PAGE_BUTTON.intValue()){
                webButton.add(temp);
            }
        }

        return Result.success().addAttribute("webPages",webPageUrl).addAttribute("webButtons",webButton).addAttribute("backUrl",backUrl);
    }



    @GetMapping("/permissionTypes")
    @ApiOperation("权限类型常量结合")
    public Result getPermissionTypes(@RequestHeader(value="token") String token, @SysUserAnnotation User user){
        return Result.success().addAttribute("permissionTypes",PermissionType.map);
    }
}
