package com.webdrp.err;

/**
 * @Author: zhang yuan ming
 * @Date: create in 下午1:23 2018/4/22
 * @mail: zh878998515@gmail.com
 * @Description:签名校验错误
 */
public class AuthRuntimeException extends RuntimeException {

    public AuthRuntimeException(String message) {
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
