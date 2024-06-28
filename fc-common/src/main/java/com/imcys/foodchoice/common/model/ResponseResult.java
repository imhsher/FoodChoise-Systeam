package com.imcys.foodchoice.common.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) //忽略传参时其他无用字段
@JsonInclude(JsonInclude.Include.NON_NULL) // 忽略反参时值为null的字段
public class ResponseResult implements Serializable {
    private int code;
    private String msg;
    private Object data;


    public ResponseResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    @ResponseStatus(HttpStatus.OK)
    public static ResponseResult success(String msg) {
        return new ResponseResult(0, msg, null);
    }

    @ResponseStatus(HttpStatus.OK)
    public static ResponseResult success(String msg, Object data) {
        return new ResponseResult(0, msg, data);
    }

    @ResponseStatus(HttpStatus.OK)
    public static ResponseResult failed(Integer errorCode, String errorMsg) {
        return new ResponseResult(errorCode, errorMsg);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static ResponseResult error(Integer errorCode, String errorMsg) {
        return new ResponseResult(errorCode, errorMsg);
    }
}
