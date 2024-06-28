package com.imcys.foodchoice.client.model.food;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imcys.foodchoice.common.core.model.BaseEntity;
import lombok.*;

@Data
public class FoodWindow extends BaseEntity {
    @JsonProperty("windowId")
    private Integer id;
    private Integer canteen;
    private Integer windowLocation;
    private Integer floor;
    private String name;
    private String picture;
}
