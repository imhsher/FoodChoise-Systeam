package com.imcys.foodchoice.client.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imcys.foodchoice.client.dao.RegionSchoolMapper;
import com.imcys.foodchoice.client.model.region.RegionSchool;
import com.imcys.foodchoice.client.service.IRegionSchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class RegionSchoolServiceImpl extends ServiceImpl<RegionSchoolMapper, RegionSchool> implements IRegionSchoolService {

    @Autowired
    private RegionSchoolMapper regionSchoolMapper;

    @Override
    public IPage<RegionSchool> getSchoolByName(Integer pn, Integer ps, String name) {
        Page<RegionSchool> page = new Page<>(pn, ps);
        return regionSchoolMapper.selectSchoolByName(page, name);
    }
}
