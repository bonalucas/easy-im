package com.easyim.common;

/**
 * 客户端业务异常
 *
 * @author 单程车票
 */
public class ServiceException extends RuntimeException{

    public ServiceException(String message) {
        super(message);
    }
}
