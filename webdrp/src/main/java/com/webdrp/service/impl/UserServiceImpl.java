package com.webdrp.service.impl;

import com.webdrp.common.BaseMapper;
import com.webdrp.common.BaseServiceImpl;
import com.webdrp.common.Pager;
import com.webdrp.entity.CommissionMode;
import com.webdrp.entity.Grade;
import com.webdrp.entity.Role;
import com.webdrp.entity.User;
import com.webdrp.entity.vo.GradeVo;
import com.webdrp.entity.vo.UserVo;
import com.webdrp.err.*;
import com.webdrp.mapper.RoleMapper;
import com.webdrp.mapper.UserMapper;
import com.webdrp.service.UserService;
import com.webdrp.util.AesUtils;
import com.webdrp.util.Sha256decode;
import com.webdrp.util.StringUtils;
import com.webdrp.util.UUIDUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created by yuanming on 2018/8/3.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User,UserMapper> implements UserService{
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;




    @Override
    public void add(User user) {
        user.setStatus(0);
        user.setPassword(Sha256decode.getSHA256Str(user.getPassword()));
        super.add(user);
    }

    //
    public String login(User user)throws Exception{
        User userData =userMapper.findByUserName(user.getUsername());
        if (Objects.isNull(userData)){
            throw new NoSuchUserError("没有此用户！");
        }
        System.out.println("user = [" + userData.getPassword() + "\n"+ Sha256decode.getSHA256Str(user.getPassword()));
        if (userData.getStatus()!=0){
            throw new LoginForBidden("拒绝登录，系统异常");
        }
        if (!userData.getPassword().equals(Sha256decode.getSHA256Str(user.getPassword()))){
            throw new PasswordError("错误密码！");
        }
        return getToken(userData);
    }

    @Override
    public void addRole(Integer roleId, Integer userId) throws Exception {
        User user = userMapper.findById(userId);
        if (Objects.isNull(user)){
            throw new NoSuchUserError("没有此用户");
        }
        Role role = roleMapper.findById(roleId);
        if (Objects.isNull(role)){
            throw new NoSuchObjectError("没有此角色");
        }
        user.setRoleId(roleId);

        User user1 = new User();
        user1.setId(user.getId());
        user1.setRoleId(roleId);
        update(user1);

    }

    //AES加密username作为key
    public String getToken(User user){
        String uuid = UUIDUtils.getUUID();
        //加密
        String username = AesUtils.encrypt(user.getUsername(),AesUtils.ADMINAESLOGINKEY);
        String hashCode = user.getUsername()+uuid;
        String sign = Sha256decode.getSHA256Str(hashCode);
        //给假的uuid
        String userToken = UUIDUtils.getUUID() + "."+username+"."+sign;
        String cacheToken = uuid + "."+username+"."+sign;
        ValueOperations<String, Object> operations=redisTemplate.opsForValue();
        System.out.println("richuser = [" + username + "]");
        operations.set(username,cacheToken,60*30, TimeUnit.SECONDS);
        operations.set(uuid,user,60*30, TimeUnit.SECONDS);
        return  userToken;
    }

    public List<UserVo> loadAll1(UserVo user, Pager pager) {
       return null;
    }

    @Override
    public void updatePassword(User user) {
        try {
            user.setPassword(Sha256decode.getSHA256Str(user.getPassword()));
            super.update(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("更新密码失败");
        }
    }
}
