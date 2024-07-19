package com.imcys.foodchoice.client.model.food;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imcys.foodchoice.common.core.model.BaseEntity;
import lombok.Data;

@Data
public class CardLikeCollection extends BaseEntity {
    @JsonProperty("likesId")
    private Integer id;
    private Integer userId;
    private Integer cardId;
    private Integer likes;
    private Integer collection;
    private Integer likesState;
}
