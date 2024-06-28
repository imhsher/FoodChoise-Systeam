package com.imcys.foodchoice.client.model.region;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegionArea {
    private Integer id;
    private Integer cityId;
    private String code;
    private String name;
}
