package com.webdrp.err;

/**
 * Created by yuanming on 2018/8/4.
 */
public class PasswordError extends RuntimeException {

    /***
     * 密码错误
     * @param message
     */
    public PasswordError(String message) {
        super(message);
    }
}
