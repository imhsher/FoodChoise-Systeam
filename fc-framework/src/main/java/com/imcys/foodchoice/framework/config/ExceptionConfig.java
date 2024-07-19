package com.imcys.foodchoice.framework.config;

import com.imcys.foodchoice.common.exception.ApiException;
import com.imcys.foodchoice.common.model.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常类配置
 */
@ControllerAdvice
@Slf4j
public class ExceptionConfig {

    @ExceptionHandler(value = ApiException.class)
    @ResponseBody
    private ResponseResult apiExceptionHandler(ApiException apiException) {
        log.info("接口层异常:{}", apiException.getErrorMsg());
        return ResponseResult.failed(apiException.getErrorCode(), apiException.getErrorMsg());
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    private ResponseResult argumentExceptionHandler(MethodArgumentNotValidException exception) {
        List<ObjectError> objectErrors = exception.getBindingResult().getAllErrors();
        String errorMessage = objectErrors.stream().map(s -> s.getDefaultMessage()).collect(Collectors.joining(";"));
        log.info("发生参数异常:{}", errorMessage);
        return ResponseResult.failed(4003, errorMessage);
    }


//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody
//    private ResponseResult argumentExceptionHandler(Exception exception) {
//        log.info("系统异常:{}", exception.getMessage());
//
//        return ResponseResult.error(4003, "系统异常：" + exception.getMessage());
//    }

}
