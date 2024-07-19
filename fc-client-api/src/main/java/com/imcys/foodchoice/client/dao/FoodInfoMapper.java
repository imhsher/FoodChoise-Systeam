package com.imcys.foodchoice.client.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imcys.foodchoice.client.controller.food.FoodInfoController;
import com.imcys.foodchoice.client.model.food.FoodCard;
import com.imcys.foodchoice.client.model.food.FoodInfo;
import com.imcys.foodchoice.client.model.food.FoodWindow;
import com.imcys.foodchoice.client.model.food.FoodWindowInfo;
import com.imcys.foodchoice.common.mapper.OptionalBaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FoodInfoMapper extends OptionalBaseMapper<FoodInfo> {

    Integer selectByUserId(Integer userId);

    IPage<FoodInfo> selectByCardId(@Param("cardId") Integer cardId, Page<FoodInfo> foodInfoIPage);
    List<FoodInfo> selectByCardIdAndShare(Integer cardId);

    @Select("SELECT * FROM fc_food_info WHERE id = #{foodId} and deleted = 0")
    Optional<FoodInfo> selectUserFoodInfoById(Integer foodId);

    @Delete("delete from fc_food_info where id = #{foodId}")
    Boolean deleteFoodInfo(Integer foodId);

    @Select("SELECT * FROM fc_canteen_food_info WHERE window_id = #{windowId}")
    List<FoodWindowInfo> selectCanteenWindowFoodInfo(Integer windowId);
}
