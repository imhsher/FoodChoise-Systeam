package com.imcys.foodchoice.client.model.food;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imcys.foodchoice.common.core.model.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FoodCardInfoMerge extends BaseEntity {
    @JsonIgnore
    private Integer id;
    @JsonIgnore
    private Integer fCardId;
    @JsonIgnore
    private Integer fId;
    @JsonIgnore
    private LocalDateTime create_time;
}
