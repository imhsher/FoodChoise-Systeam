package com.imcys.foodchoice.client.model.food;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imcys.foodchoice.client.model.region.RegionSchool;
import com.imcys.foodchoice.client.model.user.UserInfo;
import com.imcys.foodchoice.common.core.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FoodCard extends BaseEntity {
    @JsonProperty("cardId")
    private Integer id;
    @JsonIgnore
    private Integer userId;
    private String title;
    private String description;
    private String cover;
    @JsonIgnore
    private Integer schoolId;
    @JsonIgnore
    private Integer locationId;
    private LocalDateTime createTime;

    private Integer shareState;
    @JsonIgnore
    @TableLogic
    private Integer deleted;

    //补充字段
    @TableField(exist = false)
    private UserInfo userInfo;
    @TableField(exist = false)
    private RegionSchool schoolInfo;
    @TableField(exist = false)
    private FoodCardShareLocation locationInfo;


}
