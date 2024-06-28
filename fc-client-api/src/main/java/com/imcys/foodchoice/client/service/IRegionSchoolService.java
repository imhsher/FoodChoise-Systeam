package com.imcys.foodchoice.client.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imcys.foodchoice.client.model.region.RegionSchool;

public interface IRegionSchoolService {

    public IPage<RegionSchool> getSchoolByName(Integer pn, Integer ps, String name);


}
