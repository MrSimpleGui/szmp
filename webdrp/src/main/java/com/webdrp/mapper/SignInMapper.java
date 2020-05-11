package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.entity.SignIn;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: zhang yuan ming
 * @Date: create in 23:08 2020-02-21
 * @mail: zh878998515@gmail.com
 * @Description:
 */
@Component
public interface SignInMapper extends BaseMapper<SignIn> {

    /**
     * 签到
     * @param userId
     * @return
     */
    List<SignIn> findByRichUserId(Integer userId);

    /**
     * 签到次数
     * @param userId
     * @return
     */
    int findByRichUserIdCount(Integer userId);

    /**
     * 最后一次签到
     * @param userId
     * @return
     */
    SignIn findUserLastTime(Integer userId);
}
