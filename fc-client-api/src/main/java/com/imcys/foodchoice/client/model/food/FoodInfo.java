package com.imcys.foodchoice.client.model.food;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imcys.foodchoice.common.core.model.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class FoodInfo extends BaseEntity {
    @JsonProperty("foodId")
    private Integer id;
    @JsonIgnore
    private Integer userId;
    @JsonIgnore
    private Integer schoolId;
    @JsonIgnore
    private Integer locationId;
    private String name;
    private String description;
    private BigDecimal price;
    private String cover;
    @TableField(exist = false)
    private Integer cardId;

    @JsonIgnore
    private LocalDateTime createTime;
    @TableLogic
    private Integer deleted;

    @TableField(exist = false)
    private FoodClass classInfo;
    @TableField(exist = false)
    private List<FoodLabel> labels;

}
