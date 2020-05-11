package com.webdrp.mapper;


import com.webdrp.common.BaseMapper;
import com.webdrp.entity.User;
import org.springframework.stereotype.Component;


@Component
public interface UserMapper  extends BaseMapper<User> {

    User findByUserName(String username);

}
