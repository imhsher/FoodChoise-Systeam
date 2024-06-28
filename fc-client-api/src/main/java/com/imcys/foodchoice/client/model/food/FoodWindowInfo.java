package com.imcys.foodchoice.client.model.food;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imcys.foodchoice.common.core.model.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FoodWindowInfo extends BaseEntity {
    @JsonProperty("foodId")
    private Integer id;
    @JsonIgnore
    private Integer windowId;
    private String name;
    private BigDecimal price;
    private String picture;
}
