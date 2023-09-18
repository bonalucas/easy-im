package com.easyim.common;

/**
 * 客户端业务异常
 *
 * @author 单程车票
 */
public class CustomException extends RuntimeException{

    public CustomException(String message) {
        super(message);
    }
}
