package com.imcys.foodchoice.client.service.impl.food;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imcys.foodchoice.client.controller.food.FoodInfoController;
import com.imcys.foodchoice.client.dao.CardLikeCollectionMapper;
import com.imcys.foodchoice.client.dao.FoodCardMapper;
import com.imcys.foodchoice.client.model.food.*;
import com.imcys.foodchoice.client.service.food.IFoodCardService;
import com.imcys.foodchoice.common.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FoodCardServiceImpl extends ServiceImpl<FoodCardMapper, FoodCard> implements IFoodCardService {

    @Autowired
    private FoodCardMapper foodCardMapper;
    @Autowired
    private CardLikeCollectionMapper cardLikeCollectionMapper;


    @Override
    public IPage<FoodCard> findByShare(Integer pn, Integer ps) {
        Page<FoodCard> foodCardPage = new Page<>(pn, ps);
        return foodCardMapper.selectByShare(foodCardPage);
    }

    @Override
    public Integer addFoodCard(FoodInfoController.NewFoodCard newFoodCard) {

        FoodCard foodCard = new FoodCard();
        foodCard.setCover(newFoodCard.getCover());
        foodCard.setUserId(newFoodCard.getUserId());
        foodCard.setTitle(newFoodCard.getTitle());
        foodCard.setSchoolId(newFoodCard.getSchoolId());
        foodCard.setLocationId(newFoodCard.getLocationId());
        foodCard.setDescription(newFoodCard.getDescription());
        foodCard.setShareState(newFoodCard.getShareState());

        Integer result1 = foodCardMapper.insert(foodCard);


        Integer id = foodCard.getId();

        CardLikeCollection cardLikeCollection = new CardLikeCollection();
        cardLikeCollection.setCardId(id);
        cardLikeCollection.setUserId(newFoodCard.getUserId());
        cardLikeCollection.setLikes(0);
        cardLikeCollection.setCollection(0);
        cardLikeCollection.setLikesState(0);

        Integer result2 = cardLikeCollectionMapper.insert(cardLikeCollection);

        return result1 + result2 - 1;
    }

    @Override
    public Optional<FoodCard> findByIdAndShare(Integer cardId) {
        return foodCardMapper.selectByIdAndShare(cardId);
    }

    @Override
    public Optional<FoodCard> findUserCardById(Integer cardId, Integer mUserId) {
        Optional<FoodCard> foodCard = foodCardMapper.selectByIdOptional(cardId);

        if (foodCard.isPresent() && foodCard.get().getShareState() == 0 && !Objects.equals(foodCard.get().getUserId(), mUserId)) {
            throw new ApiException("这个卡片不公开哦");
        }
        return foodCard;
    }

    @Override
    public IPage<FoodCard> findUserCardByUserIdPage(Integer userId, Integer mUserId, Integer pn, Integer ps) {
        Page<FoodCard> foodCardPage = new Page<>(pn, ps);
        IPage<FoodCard> foodCardIPage;
        if (userId.equals(mUserId)) {
            foodCardIPage = foodCardMapper.selectByUserIdPage(userId, foodCardPage);
        } else {
            foodCardIPage = foodCardMapper.selectByUserIdAndSharePage(userId, foodCardPage);
        }
        return foodCardIPage;
    }

    @Override
    public Boolean updateById(Integer userId, FoodInfoController.UpdateFoodCard updateFoodCard) {
        Optional<FoodCard> foodCardOptional = foodCardMapper.selectByIdOptional(updateFoodCard.getCardId());
        if (foodCardOptional.isEmpty()) throw new ApiException("未找到卡片");
        if (!foodCardOptional.get().getUserId().equals(userId)) throw new ApiException("该卡片不属于你");
        FoodCard foodCard = foodCardOptional.get();
        foodCard.setCover(updateFoodCard.getCover());
        foodCard.setTitle(updateFoodCard.getTitle());
        foodCard.setUserId(userId);
        foodCard.setId(updateFoodCard.getCardId());
        foodCard.setShareState(updateFoodCard.getShareState());
        foodCard.setDescription(updateFoodCard.getDescription());
        foodCard.setSchoolId(updateFoodCard.getSchoolId());


        return foodCardMapper.updateById(foodCard) > 0;
    }

    @Override
    public Boolean deleteFoodCard(Integer cardId) {

        Integer exists = foodCardMapper.existsByCardId(cardId);
        if(exists != null && exists > 0){
            return foodCardMapper.deleteFoodCard(cardId);
        } else {
            return foodCardMapper.deleteFoodCardOnly(cardId);
        }

    }

    //通过食堂id查找窗口信息

    @Override
    public List<FoodWindow> findCanteenWindowInfo(Integer canteen) {
        return foodCardMapper.selectCanteenWindowInfo(canteen);
    }

    @Override
    public Optional<FoodWindow> findCanteenInfoById(Integer windowId) {
        return foodCardMapper.selectCanteenInfoById(windowId);
    }

    @Override
    public Integer addCollectionCard(FoodInfoController.CollectionCard collectionCard) {
        return foodCardMapper.addUserControllerCard(collectionCard);
    }

    @Override
    public Optional<UserCollection> findCollectionCard(Integer cardId, Integer userId) {
        return foodCardMapper.selectCollectionCardInfo(cardId, userId);
    }

    @Override
    public void updateCollectionState(Integer cardId, Integer userId, Integer collectionState) {
        foodCardMapper.updateCollectionState(cardId, userId, collectionState);
    }

    @Override
    public Optional<CardLikeCollection> getLikesCollection(Integer cardId, Integer userId) {
        return foodCardMapper.selectLikesCollection(cardId, userId);
    }

    @Override
    public void updateLikes(Integer cardId, Integer userId, Integer likes) {
        foodCardMapper.updateLikes(cardId, userId, likes);
    }

    @Override
    public void updateCollection(Integer cardId, Integer userId, Integer collection) {
        foodCardMapper.updateCollection(cardId, userId, collection);
    }

    @Override
    public void updateLikesState(Integer cardId, Integer userId, Integer likesState) {
        foodCardMapper.updateLikesState(cardId, userId, likesState);
    }

    @Override
    public List<UserCollection> getAllCollectionCard() {
        return foodCardMapper.getAllCollectionCard();
    }


}
