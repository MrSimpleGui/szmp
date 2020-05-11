package com.webdrp.err;

/**
 * @Author: zhang yuan ming
 * @Date: create in 下午1:23 2018/4/22
 * @mail: zh878998515@gmail.com
 * @Description:登录超时
 */
public class LoginTimeoutRuntimeException extends RuntimeException {

    public LoginTimeoutRuntimeException(String message) {
        super(message);
    }
}
