package com.yqsh.diningsys.core.entity;

/**
 * ServiceException : 封装业务层发生的异常
 *
 */
public class ServiceException extends RuntimeException {

    public ServiceException(String msg) {
        super(msg);
    }

}


