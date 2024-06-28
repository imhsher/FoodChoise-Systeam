package com.imcys.foodchoice.client.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imcys.foodchoice.client.model.food.FoodClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FoodClassMapper extends BaseMapper<FoodClass> {

    FoodClass selectByFoodId(Integer foodId);

    @Select("select * from fc_food_class where f_card_id = #{cardId}")
    List<FoodClass> selectByCardId(Integer cardId);

}
