package com.imcys.foodchoice.client.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imcys.foodchoice.client.controller.food.FoodInfoController;
import com.imcys.foodchoice.client.model.food.CardLikeCollection;
import com.imcys.foodchoice.client.model.food.FoodCard;
import com.imcys.foodchoice.client.model.food.FoodWindow;
import com.imcys.foodchoice.client.model.food.UserCollection;
import com.imcys.foodchoice.common.mapper.OptionalBaseMapper;
import lombok.Getter;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FoodCardMapper extends OptionalBaseMapper<FoodCard> {


    IPage<FoodCard> selectByShare(Page<FoodCard> page);

    Optional<FoodCard> selectByIdAndShare(Integer cardId);

    @Select("select * from fc_food_card where user_id = #{userId}")
    List<FoodCard> selectByUserId(Integer userId);

    @Select("select * from fc_food_card where user_id = #{userId} and share_state = 1")
    List<FoodCard> selectByUserIdAndShare(Integer userId);


    @Select("select * from fc_food_card where user_id = #{userId}")
    IPage<FoodCard> selectByUserIdPage(@Param("userId") Integer userId, Page<FoodCard> foodCardIPage);

    @Select("select * from fc_food_card where user_id = #{userId} and share_state = 1")
    IPage<FoodCard> selectByUserIdAndSharePage(@Param("userId") Integer userId, Page<FoodCard> foodCardIPage);

    @Select("select COUNT(*) from fc_food_card_info_merge where f_card_id=#{cardId}")
    Integer  existsByCardId(Integer cardId);
    @Delete("DELETE fc_food_card, fc_food_info, fc_food_card_info_merge\n" +
            "FROM fc_food_card\n" +
            "JOIN fc_food_card_info_merge ON fc_food_card.id = fc_food_card_info_merge.f_card_id\n" +
            "JOIN fc_food_info ON fc_food_card_info_merge.f_id = fc_food_info.id\n" +
            "WHERE fc_food_card.id = #{cardId} and fc_food_card_info_merge.f_card_id=#{cardId} ")
    Boolean deleteFoodCard(Integer cardId);

    @Delete("DELETE FROM fc_food_card WHERE id = #{cardId}")
    Boolean deleteFoodCardOnly(Integer cardId);

    @Select("SELECT * FROM fc_canteen_food_window where canteen = #{canteen}")
    List<FoodWindow> selectCanteenWindowInfo(Integer canteen);

    @Select("select * from fc_canteen_food_window where id = #{windowId}")
    Optional<FoodWindow> selectCanteenInfoById(Integer windowId);

    @Insert("INSERT INTO fc_user_collection (card_id, user_id, title, description, cover, collection_state) VALUES (#{cardId}, #{userId}, #{title}, #{description}, #{cover}, 0)")
    Integer addUserControllerCard(FoodInfoController.CollectionCard collectionCard);

    @Select("select * from fc_user_collection where card_id=#{cardId} and user_id = #{userId}")
    Optional<UserCollection> selectCollectionCardInfo(@Param("cardId")Integer cardId, @Param("userId")Integer userId);

    @Update("update fc_user_collection set collection_state = #{collectionState} where card_id=#{cardId} and user_id = #{userId}")
    void updateCollectionState(@Param("cardId")Integer cardId, @Param("userId")Integer userId, @Param("collectionState")Integer collectionState);

    @Select("select * from fc_card_like_collection  where card_id=#{cardId} and user_id = #{userId}")
    Optional<CardLikeCollection> selectLikesCollection(@Param("cardId")Integer cardId, @Param("userId")Integer userId);

    @Update("update fc_card_like_collection set likes = #{likes} where card_id=#{cardId} and user_id = #{userId}" )
    void updateLikes(@Param("cardId")Integer cardId, @Param("userId")Integer userId, @Param("likes")Integer likes);
    @Update("update fc_card_like_collection set collection = #{collection} where card_id=#{cardId} and user_id = #{userId}" )
    void updateCollection(@Param("cardId")Integer cardId, @Param("userId")Integer userId, @Param("collection")Integer collection);

    @Update("update fc_card_like_collection set likes_state = #{likesState} where card_id=#{cardId} and user_id = #{userId}" )
    void updateLikesState(@Param("cardId")Integer cardId, @Param("userId")Integer userId, @Param("likesState")Integer likesState);

    @Select("select * from fc_user_collection where collection_state = 1 ")
    List<UserCollection> getAllCollectionCard();



}
