package com.webdrp.err;

/**
 * Created by yuanming on 2018/8/8.
 * 权限不足
 */
public class PermissionException  extends Exception {
    /**
     */
    public PermissionException(String message) {
        super(message);
    }
}
