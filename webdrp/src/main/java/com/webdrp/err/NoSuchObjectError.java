package com.webdrp.err;

/**
 * Created by yuanming on 2018/8/4.
 * 没有这个对象
 */
public class NoSuchObjectError extends Exception{
    /**

     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public NoSuchObjectError(String message) {
        super(message);
    }
}
