package com.imcys.foodchoice.client.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imcys.foodchoice.client.model.food.FoodClass;
import com.imcys.foodchoice.client.model.food.FoodLabel;
import com.imcys.foodchoice.common.mapper.OptionalBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FoodLabelMapper extends BaseMapper<FoodLabel> {
//    @Select("select * from fc_food_label where f_card_id = #{cardId}")
    List<FoodLabel> selectByCardId(Integer cardId);

}
