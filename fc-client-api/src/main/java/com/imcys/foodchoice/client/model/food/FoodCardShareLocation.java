package com.imcys.foodchoice.client.model.food;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imcys.foodchoice.common.core.model.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FoodCardShareLocation extends BaseEntity {
    @JsonIgnore
    private Integer id;
    private Double latitude;
    private Double longitude;
    private String address;
    private LocalDateTime createTime;

}
