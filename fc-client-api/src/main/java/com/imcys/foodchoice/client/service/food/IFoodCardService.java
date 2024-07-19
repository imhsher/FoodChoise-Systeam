package com.imcys.foodchoice.client.service.food;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imcys.foodchoice.client.controller.food.FoodInfoController;
import com.imcys.foodchoice.client.model.food.CardLikeCollection;
import com.imcys.foodchoice.client.model.food.FoodCard;
import com.imcys.foodchoice.client.model.food.FoodWindow;
import com.imcys.foodchoice.client.model.food.UserCollection;

import java.util.List;
import java.util.Optional;


public interface IFoodCardService extends IService<FoodCard> {

    IPage<FoodCard> findByShare(Integer pn, Integer ps);

    Integer addFoodCard(FoodInfoController.NewFoodCard newFoodCard);

    Optional<FoodCard> findByIdAndShare(Integer cardId);

    Optional<FoodCard> findUserCardById(Integer cardId, Integer mUserId);

    IPage<FoodCard> findUserCardByUserIdPage(Integer userId, Integer mUserId, Integer pn, Integer ps);

    Boolean updateById(Integer userId, FoodInfoController.UpdateFoodCard updateFoodCard);

    Boolean deleteFoodCard(Integer cardId);

    List<FoodWindow> findCanteenWindowInfo(Integer canteen);

    Optional<FoodWindow> findCanteenInfoById(Integer windowId);

    Integer addCollectionCard(FoodInfoController.CollectionCard collectionCard);

    Optional<UserCollection> findCollectionCard(Integer cardId, Integer userId);

    void updateCollectionState(Integer cardId, Integer userId, Integer collectionState);

    Optional<CardLikeCollection> getLikesCollection(Integer cardId, Integer userId);

    void updateLikes(Integer cardId, Integer userId, Integer likes);

    void updateCollection(Integer cardId, Integer userId, Integer collection);


    void updateLikesState(Integer cardId, Integer userId, Integer likesState);

    List<UserCollection> getAllCollectionCard();
}
