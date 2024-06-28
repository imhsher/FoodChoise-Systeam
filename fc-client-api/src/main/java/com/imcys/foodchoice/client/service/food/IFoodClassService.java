package com.imcys.foodchoice.client.service.food;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imcys.foodchoice.client.controller.food.FoodClassLabelController;
import com.imcys.foodchoice.client.model.food.FoodClass;

import java.util.List;

public interface IFoodClassService extends IService<FoodClass> {
    boolean addFoodClass(FoodClassLabelController.NewFoodClass newFoodClass);

    boolean addFoodToClassVerifyUserId(Integer classId, Integer foodId, Integer userId);

    List<FoodClass> findByCardId(Integer cardId);
}
