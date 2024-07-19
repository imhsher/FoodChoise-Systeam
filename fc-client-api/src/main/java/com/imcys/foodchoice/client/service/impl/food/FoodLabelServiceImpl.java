package com.imcys.foodchoice.client.service.impl.food;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imcys.foodchoice.client.controller.food.FoodClassLabelController;
import com.imcys.foodchoice.client.dao.FoodCardMapper;
import com.imcys.foodchoice.client.dao.FoodLabelInfoMergeMapper;
import com.imcys.foodchoice.client.dao.FoodLabelMapper;
import com.imcys.foodchoice.client.model.food.FoodClass;
import com.imcys.foodchoice.client.model.food.FoodLabel;
import com.imcys.foodchoice.client.model.food.FoodLabelInfoMerge;
import com.imcys.foodchoice.client.service.food.IFoodLabelService;
import com.imcys.foodchoice.common.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodLabelServiceImpl extends ServiceImpl<FoodLabelMapper, FoodLabel> implements IFoodLabelService {
    @Autowired
    private FoodLabelMapper foodLabelMapper;

    @Autowired
    private FoodCardMapper foodCardMapper;

    @Autowired
    private FoodLabelInfoMergeMapper foodLabelInfoMergeMapper;

    @Override
    public boolean addFoodLabel(FoodClassLabelController.NewFoodLabel newFoodLabel) {
        foodCardMapper.selectByIdOptional(newFoodLabel.getCardId()).orElseThrow(() -> new ApiException("不存在该卡片"));
        FoodLabel foodLabel = new FoodLabel();
        foodLabel.setFCardId(newFoodLabel.getCardId());
        foodLabel.setName(newFoodLabel.getName());
        foodLabel.setUserId(newFoodLabel.getUserId());
        return foodLabelMapper.insert(foodLabel) > 0;
    }

    @Override
    public List<FoodLabel> findByCardId(Integer cardId) {
        return foodLabelMapper.selectByCardId(cardId);
    }

    @Override
    public boolean addFoodToLabelVerifyUserId(Integer labelId, Integer foodId, Integer userId) {

        FoodLabel foodLabel = foodLabelMapper.selectById(labelId);

        if (foodLabel == null || !foodLabel.getUserId().equals(userId)) {
            throw new ApiException("这不是你的标签哦");
        }

        FoodLabelInfoMerge foodLabelInfoMerge = new FoodLabelInfoMerge();
        foodLabelInfoMerge.setFId(foodId);
        foodLabelInfoMerge.setFLabelId(labelId);


        return foodLabelInfoMergeMapper.insert(foodLabelInfoMerge) > 0;
    }

}
