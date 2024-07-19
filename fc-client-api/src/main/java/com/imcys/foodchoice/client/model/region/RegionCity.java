package com.imcys.foodchoice.client.model.region;

import com.imcys.foodchoice.common.core.model.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegionCity  extends BaseEntity {
    private Integer id;
    private Integer provinceId;

    private String code;
    private String name;
}
