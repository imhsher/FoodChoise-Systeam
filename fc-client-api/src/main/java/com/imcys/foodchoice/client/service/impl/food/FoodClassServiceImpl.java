package com.imcys.foodchoice.client.service.impl.food;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imcys.foodchoice.client.controller.food.FoodClassLabelController;
import com.imcys.foodchoice.client.dao.*;
import com.imcys.foodchoice.client.model.food.FoodClass;
import com.imcys.foodchoice.client.model.food.FoodClassInfoMerge;
import com.imcys.foodchoice.client.model.food.FoodLabel;
import com.imcys.foodchoice.client.model.food.FoodLabelInfoMerge;
import com.imcys.foodchoice.client.service.food.IFoodClassService;
import com.imcys.foodchoice.common.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FoodClassServiceImpl extends ServiceImpl<FoodClassMapper, FoodClass> implements IFoodClassService {
    @Autowired
    private FoodClassMapper foodClassMapper;
    @Autowired
    private FoodCardMapper foodCardMapper;
    @Autowired
    private FoodClassInfoMergeMapper foodClassInfoMergeMapper;
    @Autowired
    private FoodLabelInfoMergeMapper foodLabelInfoMergeMapper;

    @Override
    public boolean addFoodClass(FoodClassLabelController.NewFoodClass newFoodClass) {
        foodCardMapper.selectByIdOptional(newFoodClass.getCardId()).orElseThrow(() -> new ApiException("不存在该卡片"));
        FoodClass foodClass = new FoodClass();
        foodClass.setFCardId(newFoodClass.getCardId());
        foodClass.setName(newFoodClass.getName());
        foodClass.setUserId(newFoodClass.getUserId());


        return foodClassMapper.insert(foodClass) > 0;
    }

    @Override
    public boolean addFoodToClassVerifyUserId(Integer classId, Integer foodId, Integer userId) {
        return false;
    }

    @Override
    public List<FoodClass> findByCardId(Integer cardId) {
        return null;
    }

//    @Override
//    public boolean addFoodToClassVerifyUserId(Integer classId, Integer foodId, Integer userId) {
//        FoodClass foodClass = foodClassMapper.selectById(classId);
//
//        if (foodClass == null || !foodClass.getUserId().equals(userId)) {
//            throw new ApiException("这不是你的分类哦");
//        }
//
//        FoodClassInfoMerge foodClassInfoMerge = foodClassInfoMergeMapper.selectByFoodIdAndClassId(classId, foodId);
//
//        if (foodClassInfoMerge != null) throw new ApiException("该食物已经被添加到了该分类");
//
//        foodClassInfoMerge = new FoodClassInfoMerge();
//        foodClassInfoMerge.setFClassId(classId);
//        foodClassInfoMerge.setFId(foodId);
//
//
//        return foodClassInfoMergeMapper.insert(foodClassInfoMerge) > 0;
//    }


//    @Override
//    public List<FoodClass> findByCardId(Integer cardId) {
//        return foodClassMapper.selectByCardId(cardId);
//    }


}
