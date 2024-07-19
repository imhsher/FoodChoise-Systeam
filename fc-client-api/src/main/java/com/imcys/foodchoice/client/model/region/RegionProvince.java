package com.imcys.foodchoice.client.model.region;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imcys.foodchoice.common.core.model.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegionProvince extends BaseEntity {
    @JsonIgnore
    private Integer id;
    @JsonProperty("provinceCode")
    private String code;
    private String name;
    @JsonIgnore
    private RegionCity regionCity;
}
