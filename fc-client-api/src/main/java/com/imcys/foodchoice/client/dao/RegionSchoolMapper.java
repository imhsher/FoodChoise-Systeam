package com.imcys.foodchoice.client.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imcys.foodchoice.client.model.region.RegionSchool;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RegionSchoolMapper extends BaseMapper<RegionSchool> {
    public IPage<RegionSchool> selectSchoolByName(Page<RegionSchool> page,@Param("name") String name);

}
