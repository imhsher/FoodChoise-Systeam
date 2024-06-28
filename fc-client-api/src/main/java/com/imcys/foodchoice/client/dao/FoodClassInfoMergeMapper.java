package com.imcys.foodchoice.client.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imcys.foodchoice.client.model.food.FoodClassInfoMerge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FoodClassInfoMergeMapper extends BaseMapper<FoodClassInfoMerge> {

//
//    FoodClassInfoMerge selectByFoodIdAndClassId(@Param("classId") Integer classId, @Param("foodId") Integer foodId);
}
