package com.imcys.foodchoice.client.model.region;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imcys.foodchoice.common.core.model.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegionSchool extends BaseEntity {
    @JsonIgnore
    private Integer id;
    @JsonIgnore
    private Integer provinceId;
    @JsonIgnore
    private Integer cityId;

    @JsonProperty("schoolId")
    private String code;
    private String name;
    private String department;

    private RegionProvince regionProvince;
}
