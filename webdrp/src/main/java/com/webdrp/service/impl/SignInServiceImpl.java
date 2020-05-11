package com.webdrp.service.impl;

import com.webdrp.common.BaseServiceImpl;
import com.webdrp.entity.SignIn;
import com.webdrp.mapper.SignInMapper;
import com.webdrp.service.SignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zhang yuan ming
 * @Date: create in 23:44 2020-02-21
 * @mail: zh878998515@gmail.com
 * @Description:签到
 */
@Service
public class SignInServiceImpl extends BaseServiceImpl<SignIn,SignInMapper> implements SignInService {

    @Autowired
    SignInMapper signInMapper;
    /**
     * 签到
     *
     * @param userId
     * @return
     */
    @Override
    public List<SignIn> findByRichUserId(Integer userId) {
        return getE().findByRichUserId(userId);
    }

    /**
     * 签到次数
     *
     * @param userId
     * @return
     */
    @Override
    public int findByRichUserIdCount(Integer userId) {
        return getE().findByRichUserIdCount(userId);
    }

    /**
     * 最后一次签到
     *
     * @param userId
     * @return
     */
    @Override
    public SignIn findUserLastTime(Integer userId) {
        return getE().findUserLastTime(userId);
    }
}
