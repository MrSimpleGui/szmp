package com.webdrp.controller.sys;

import com.webdrp.annotation.SysUserAnnotation;
import com.webdrp.common.BaseController;
import com.webdrp.common.BaseService;
import com.webdrp.common.Pager;
import com.webdrp.common.Result;
import com.webdrp.entity.Role;
import com.webdrp.entity.User;
import com.webdrp.service.RoleService;
import com.webdrp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by yuanming on 2018/8/2.
 */
@Api(tags = "后台接口用户相关")
@RestController
@RequestMapping("/sys/user")
public class UserController extends BaseController<User,Integer> {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;
    /**
     * 抽象业务类  T 实体对象   ID为
     * @return
     */
    @Override
    public BaseService<User> getService() {
        return userService;
    }

    @ApiOperation("登录接口")
    @PostMapping("/login")
    public Result login(@RequestParam String username,@RequestParam String password) throws Exception {
        User user = new User(username,password);
        return Result.success(userService.login(user));
    }

    @ApiOperation("设置用户角色")
    @PostMapping("/setRole")
    public Result setRole(@RequestHeader(value="token") String token, @RequestParam Integer roleId, @RequestParam Integer userId, @SysUserAnnotation User user) throws Exception {
        userService.addRole(roleId,userId);
        return Result.success(null);
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/getUserInfo")
    public Result getData(@RequestHeader(value="token") String token, @SysUserAnnotation User user){
        Map<String,Object> map = new HashMap<>();
        map.put("user",user);
        user = userService.findOne(user.getId());
        Role role = roleService.findOne(user.getRoleId());
        if (Objects.isNull(role)){
            role = new Role();
        }
        map.put("role",role);
        return Result.success(map);
    }

    @ApiOperation("获取所有用户")
    @Override
    public Result loadAll(String token, User user, Pager pager) {
        return super.loadAll(token, user, pager);
    }

    @ApiOperation("更新登录密码")
    @GetMapping("/updatePassword")
    public Result updatePassword(@RequestHeader(value="token") String token, @SysUserAnnotation User user,
                                 @RequestParam String pwd){
        user.setPassword(pwd);
        userService.updatePassword(user);
        return Result.success();
    }

}
