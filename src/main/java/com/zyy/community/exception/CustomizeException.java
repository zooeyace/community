package com.zyy.community.exception;

/**
 *  自定义异常类
 */

public class CustomizeException extends RuntimeException {

    private final String message;
    private Integer code;

    /**
     *  message赋值为枚举类型的errorCode，
     *  以后只需要在枚举类里面新增错误信息就可以，不用在每次抛出异常的时候手写异常信息
     * @param errorCode error info
     */
    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
