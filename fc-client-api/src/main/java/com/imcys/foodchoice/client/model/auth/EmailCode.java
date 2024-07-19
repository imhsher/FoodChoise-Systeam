package com.imcys.foodchoice.client.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imcys.foodchoice.common.core.model.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmailCode extends BaseEntity {
    @JsonIgnore
    private Integer id;
    private String email;
    @JsonIgnore
    private String authCode;
    @JsonIgnore
    private String code;
    @JsonIgnore
    private Integer tag;
    @JsonIgnore
    private Integer state;
    @JsonIgnore
    private LocalDateTime createTime;
    @JsonIgnore
    private LocalDateTime endTime;
}
