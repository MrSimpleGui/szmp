package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.common.Pager;
import com.webdrp.entity.User;
import com.webdrp.entity.vo.UserVo;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by yuanming on 2018/8/2.
 */
@Service
public interface UserService extends BaseService<User> {

    String login(User user)throws Exception;
    //
    void addRole(Integer roleId,Integer userId)throws Exception;

    List<UserVo> loadAll1(UserVo t,Pager pager);

    void updatePassword(User user);
}
