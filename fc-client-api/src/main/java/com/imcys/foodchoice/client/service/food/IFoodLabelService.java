package com.imcys.foodchoice.client.service.food;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imcys.foodchoice.client.controller.food.FoodClassLabelController;
import com.imcys.foodchoice.client.model.food.FoodLabel;

import java.util.List;

public interface IFoodLabelService extends IService<FoodLabel> {

    boolean addFoodLabel(FoodClassLabelController.NewFoodLabel newFoodLabel);

    List<FoodLabel> findByCardId(Integer cardId);


    boolean addFoodToLabelVerifyUserId(Integer labelId, Integer foodId, Integer userId);

}
