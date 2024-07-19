package com.imcys.foodchoice.client.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imcys.foodchoice.common.core.model.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class UserToken extends BaseEntity {
    @JsonIgnore
    private Integer id;
    @JsonIgnore
    private Integer userId;
    private String token;


    private Date createTime;
}
