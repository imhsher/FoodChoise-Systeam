package com.imcys.foodchoice.common.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class PageDataModel {
    private List<?> item;
    private Boolean more;
    private Long size;
    private Long current;
    @JsonIgnore
    private Long total;
    @JsonIgnore
    private Integer searchCount;
    @JsonIgnore
    private Integer maxLimit;
    @JsonIgnore
    private Integer countId;
    private Long pages;
}
