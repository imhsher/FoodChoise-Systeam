package com.imcys.foodchoice.client.model.food;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imcys.foodchoice.common.core.model.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FoodClass extends BaseEntity {
    @JsonIgnore
    private Integer id;
    private String name;
    @JsonIgnore
    private Integer fCardId;
    @JsonIgnore
    private Integer userId;
    @JsonIgnore
    private LocalDateTime createTime;
    @TableLogic
    @JsonIgnore
    private Integer deleted;
}
