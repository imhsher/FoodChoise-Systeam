package com.imcys.foodchoice.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 全局异常粗略
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApiException extends RuntimeException {

    private Integer errorCode = 4000;
    private String errorMsg = "接口层异常";

    public ApiException() {
    }

    public ApiException(String message) {
        super(message);
        this.errorMsg = message;
    }

    public ApiException(Integer errorCode, String message) {
        super(message);
        this.errorMsg = message;
        this.errorCode = errorCode;
    }

    public ApiException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getResultMsg());
        this.errorMsg = exceptionEnum.getResultMsg();
        this.errorCode = exceptionEnum.getResultCode();
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
        this.errorMsg = message;
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorMsg = message;
    }
}
