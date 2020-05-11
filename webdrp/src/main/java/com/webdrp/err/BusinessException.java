package com.webdrp.err;

import java.io.Serializable;

public class BusinessException extends  RuntimeException implements Serializable {

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public BusinessException(){

    }
    public BusinessException(String msg){
        super(msg);
    }
    public BusinessException(String msg,Throwable e){
        super(msg,e);
    }
}
