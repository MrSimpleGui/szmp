package com.webdrp.err;

/**
 * Created by yuanming on 2018/8/4.
 */
public class NoSuchUserError extends RuntimeException{
    /**

     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public NoSuchUserError(String message) {
        super(message);
    }
}
