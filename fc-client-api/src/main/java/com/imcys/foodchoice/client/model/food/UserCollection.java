package com.imcys.foodchoice.client.model.food;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imcys.foodchoice.common.core.model.BaseEntity;
import lombok.Data;

@Data
public class UserCollection  extends BaseEntity {
    @JsonProperty("collectionId")
    private Integer id;
    private Integer cardId;
    private Integer userId;
    private String title;
    private String description;
    private String cover;
    private Integer collectionState;
}
