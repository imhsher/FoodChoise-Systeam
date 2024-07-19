package com.imcys.foodchoice.client.dao;

import com.imcys.foodchoice.client.model.food.FoodClassInfoMerge;
import com.imcys.foodchoice.client.model.food.FoodLabelInfoMerge;
import com.imcys.foodchoice.common.mapper.OptionalBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FoodLabelInfoMergeMapper extends OptionalBaseMapper<FoodLabelInfoMerge> {
//    @Select("select * from fc_food_label_info_merge where f_id = #{foodId} and f_label_id = #{labelId}")
//    FoodClassInfoMerge selectByFoodIdAndLabelId(@Param("foodId") Integer foodId, @Param("labelId") Integer labelId);
}
