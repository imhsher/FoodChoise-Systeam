package com.imcys.foodchoice.client.model.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imcys.foodchoice.client.model.region.RegionSchool;
import com.imcys.foodchoice.common.core.model.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class UserInfo extends BaseEntity {

    @JsonProperty("userId")
    private Integer id;
    @JsonProperty("name")
    private String username;
    @JsonIgnore
    private String password;
    private String nickname;
    private String headPic;
    private String personalityTags;
//    @JsonIgnore
    private String phone;
    @JsonIgnore
    private String email;
    private Integer gender;
    private Integer schoolId;
    @TableField(exist = false)
    private RegionSchool schoolInfo;
    @JsonIgnore
    private Date regDate;
    @JsonIgnore
    private LocalDateTime updateDate;
    @TableLogic
    @JsonIgnore
    private Integer deleted;

}
