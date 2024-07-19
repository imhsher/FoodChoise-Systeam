package com.imcys.foodchoice.client.service.food;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imcys.foodchoice.client.controller.food.FoodInfoController;
import com.imcys.foodchoice.client.model.food.FoodInfo;
import com.imcys.foodchoice.client.model.food.FoodWindowInfo;

import java.util.List;
import java.util.Optional;

public interface IFoodInfoService extends IService<FoodInfo> {
    Integer addFoodInfo(FoodInfoController.NewFoodInfo newFoodInfo);
    IPage<FoodInfo> findFoodByCardId(Integer cardId,Integer userId, Integer pn, Integer ps);

    IPage<FoodInfo> findFoodsByCardIdAndShare(Integer cardId, Integer userId,Integer pn, Integer ps);


    Optional<FoodInfo> findUserFoodInfoById(Integer foodId);

    Boolean updateByFoodId(Integer id ,FoodInfoController.UpdateFoodInfo updateFoodInfo);


    Boolean deleteFoodInfo(Integer foodId);


    List<FoodWindowInfo> findFoodInfoById(Integer windowId);
}
