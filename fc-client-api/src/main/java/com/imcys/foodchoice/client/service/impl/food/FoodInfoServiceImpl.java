package com.imcys.foodchoice.client.service.impl.food;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imcys.foodchoice.client.controller.food.FoodInfoController;
import com.imcys.foodchoice.client.dao.FoodCardInfoMergeMapper;
import com.imcys.foodchoice.client.dao.FoodCardMapper;
import com.imcys.foodchoice.client.dao.FoodInfoMapper;
import com.imcys.foodchoice.client.dao.CardLikeCollectionMapper;
import com.imcys.foodchoice.client.model.food.*;
import com.imcys.foodchoice.client.service.food.IFoodInfoService;
import com.imcys.foodchoice.common.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FoodInfoServiceImpl extends ServiceImpl<FoodInfoMapper, FoodInfo> implements IFoodInfoService {

    @Autowired
    private FoodInfoMapper foodInfoMapper;
    @Autowired
    private FoodCardMapper foodCardMapper;
    @Autowired
    private FoodCardInfoMergeMapper foodCardInfoMergeMapper;


    @Override
    @Transactional
    public Integer addFoodInfo(FoodInfoController.NewFoodInfo newFoodInfo) {

        FoodCard foodCard = foodCardMapper.selectById(newFoodInfo.getCardId());
        if (foodCard != null && !foodCard.getUserId().equals(newFoodInfo.getUserId())) {
            throw new ApiException("该卡片不属于你哦" + newFoodInfo.getUserId());
        }


        FoodInfo foodInfo = new FoodInfo();
        foodInfo.setUserId(newFoodInfo.getUserId());
        foodInfo.setName(newFoodInfo.getName());
        foodInfo.setDescription(newFoodInfo.getDescription());
        foodInfo.setPrice(newFoodInfo.getPrice());
        foodInfo.setSchoolId(newFoodInfo.getSchoolId());
        foodInfo.setCover(newFoodInfo.getCover());
        foodInfo.setLocationId(newFoodInfo.getLocationId());

        Integer result1 = foodInfoMapper.insert(foodInfo);

        FoodCardInfoMerge foodCardInfoMerge = new FoodCardInfoMerge();
        foodCardInfoMerge.setFCardId(newFoodInfo.getCardId());
        foodCardInfoMerge.setFId(foodInfo.getId());

        Integer result2 = foodCardInfoMergeMapper.insert(foodCardInfoMerge);


        //也就是说两次插入都要成功
        return result1 + result2 - 1;
    }

    @Override
    public IPage<FoodInfo> findFoodByCardId(Integer cardId, Integer userId, Integer pn, Integer ps) {

        Optional<FoodCard> foodCard = foodCardMapper.selectByIdOptional(cardId);

        foodCard.orElseThrow(() -> new ApiException("没有这样卡片哦"));

        if (!Objects.equals(foodCard.get().getUserInfo().getId(), userId)) {
            throw new ApiException("该卡片不属于你");
        }

        Page<FoodInfo> foodInfoPage = new Page<>(pn, ps);

        return foodInfoMapper.selectByCardId(cardId, foodInfoPage);
    }

    @Override
    public IPage<FoodInfo> findFoodsByCardIdAndShare(Integer cardId, Integer userId,Integer pn, Integer ps) {
        Optional<FoodCard> foodCard = foodCardMapper.selectByIdOptional(cardId);

        foodCard.orElseThrow(() -> new ApiException("没有这样卡片哦"));

        if (foodCard.get().getShareState() != 1 && !foodCard.get().getUserId().equals(userId)) throw new ApiException("该卡片未公开哦");

        Page<FoodInfo> foodInfoPage = new Page<>(pn, ps);

        return foodInfoMapper.selectByCardId(cardId, foodInfoPage);
    }

    @Override
    public Optional<FoodInfo> findUserFoodInfoById(Integer foodId) {
        return foodInfoMapper.selectUserFoodInfoById(foodId);
    }

    @Override
    public Boolean updateByFoodId(Integer Id, FoodInfoController.UpdateFoodInfo updateFoodInfo) {
        Optional<FoodInfo> foodInfoOptional = foodInfoMapper.selectByIdOptional(updateFoodInfo.getFoodId());
        if (foodInfoOptional.isEmpty()) throw new ApiException("未找到卡片");
        FoodInfo foodInfo = foodInfoOptional.get();
        foodInfo.setCover(updateFoodInfo.getCover());
        foodInfo.setName(updateFoodInfo.getName());
        foodInfo.setId(Id);
        foodInfo.setPrice(BigDecimal.valueOf(updateFoodInfo.getPrice()));
        foodInfo.setDescription(updateFoodInfo.getDescription());
        return foodInfoMapper.updateById(foodInfo) > 0;
    }

    @Override
    public Boolean deleteFoodInfo(Integer foodId) {
        return foodInfoMapper.deleteFoodInfo(foodId);
    }



    //通过窗口id查找食物信息
    @Override
    public List<FoodWindowInfo> findFoodInfoById(Integer windowId) {
        return foodInfoMapper.selectCanteenWindowFoodInfo(windowId);
    }

}
