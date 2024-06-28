package com.imcys.foodchoice.client.controller.food;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imcys.foodchoice.client.ann.ClientAuth;
import com.imcys.foodchoice.client.model.food.*;
import com.imcys.foodchoice.client.service.auth.IUserTokenService;
import com.imcys.foodchoice.client.service.food.IFoodCardService;
import com.imcys.foodchoice.client.service.food.IFoodClassService;
import com.imcys.foodchoice.client.service.food.IFoodInfoService;
import com.imcys.foodchoice.client.service.food.IFoodLabelService;
import com.imcys.foodchoice.client.utils.UserUtils;
import com.imcys.foodchoice.common.core.controller.BaseController;
import com.imcys.foodchoice.common.exception.ApiException;
import com.imcys.foodchoice.common.model.ResponseResult;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * 该类承担foodCard和foodInfo的新增，删除等职责。
 */
@RestController
@RequestMapping("api/v1/client/food")
@Validated
public class FoodInfoController extends BaseController {

    @Autowired
    private IFoodCardService foodCardService;
    @Autowired
    private IFoodInfoService foodInfoService;
    @Autowired
    private IFoodClassService foodClassService;
    @Autowired
    private IFoodLabelService foodLabelService;
    @Autowired
    private IUserTokenService userTokenService;
    @Autowired
    private UserUtils userUtils;


    @Data
    public static class NewFoodCard {
        private Integer userId;
        @NotBlank(message = "标题不可为空")
        private String title;
        private String description;
        private String cover;
        private Integer schoolId;
        private Integer locationId;
        private Integer shareState;

    }

    @ClientAuth
    @PostMapping("/newCard")
    public ResponseResult newCard(HttpServletRequest request, @RequestBody NewFoodCard newFoodCard) {

        Integer userId = UserUtils.getUserId(request);
        newFoodCard.setUserId(userId);

        Integer result = foodCardService.addFoodCard(newFoodCard);

        if (result < 0) return ResponseResult.failed(5000, "服务器错误");
        return ResponseResult.success("添加成功");
    }


    @Data
    public static class NewFoodInfo {
        private Integer userId;
        private Integer schoolId;
        private Integer cardId;
        private Integer locationId;
        @NotBlank(message = "名称不可为空")
        private String name;
        private String description;
        @NotBlank(message = "价格不可为空")
        private BigDecimal price;
        private String cover;
    }

    @ClientAuth
    @PostMapping("/newFood")
    public ResponseResult newFood(HttpServletRequest request, @RequestBody NewFoodInfo newFoodInfo) {

        Integer userId = UserUtils.getUserId(request);
        newFoodInfo.setUserId(userId);
        Integer result = foodInfoService.addFoodInfo(newFoodInfo);

        if (result < 0) return ResponseResult.failed(5000, "服务器错误");
        return ResponseResult.success("添加成功");
    }

    @ClientAuth
    @GetMapping("/getCardFood")
    public ResponseResult getCardFood(@RequestParam("cardId") Integer cardId, @RequestParam(value = "pn", required = false, defaultValue = "1") @Min(value = 1, message = "超过最小范围") Integer pn, @RequestParam(value = "ps", required = false, defaultValue = "20") @Max(value = 20, message = "超过最大范围") Integer ps, HttpServletRequest request) {
        Integer userId = UserUtils.getUserId(request);
        IPage<FoodInfo> foodInfoList = foodInfoService.findFoodByCardId(cardId, userId, pn, ps);
        return tableResult("获取成功", foodInfoList);
    }

    @GetMapping("/getShareCardFood")
    @ClientAuth
    public ResponseResult getShareCardFood(@RequestParam("cardId") Integer cardId, @RequestParam(value = "pn", required = false, defaultValue = "1") @Min(value = 1, message = "超过最小范围") Integer pn, @RequestParam(value = "ps", required = false, defaultValue = "20") @Max(value = 20, message = "超过最大范围") Integer ps, HttpServletRequest request) {
        Integer userId = UserUtils.getUserId(request);
        IPage<FoodInfo> foodInfoList = foodInfoService.findFoodsByCardIdAndShare(cardId, userId, pn, ps);
        return tableResult("获取成功", foodInfoList);
    }

    @GetMapping("/getCardClass")
    @ClientAuth
    public ResponseResult getCardClass(@RequestParam("cardId") @NotNull(message = "卡片不可为空") Integer cardId, HttpServletRequest request) {
        List<FoodClass> foodClassList = foodClassService.findByCardId(cardId);
        return ResponseResult.success("获取成功", foodClassList);
    }

    @GetMapping("/getCardLabel")
    @ClientAuth
    public ResponseResult getCardLabel(@RequestParam("cardId") @NotNull(message = "卡片不可为空") Integer cardId, HttpServletRequest request) {
        List<FoodLabel> foodLabelList = foodLabelService.findByCardId(cardId);
        return ResponseResult.success("获取成功", foodLabelList);
    }


    @PostMapping("/addClassFood")
    @ClientAuth
    public ResponseResult addClassFood(@RequestParam("classId") @NotNull(message = "分类不可为空") Integer classId, @RequestParam("foodId") @NotNull(message = "食物不可为空") Integer foodId, HttpServletRequest request) {

        Integer userId = UserUtils.getUserId(request);

        boolean result = foodClassService.addFoodToClassVerifyUserId(classId, foodId, userId);

        if (result) {
            return ResponseResult.success("添加成功");
        }

        return ResponseResult.failed(4000, "添加失败");
    }

    @PostMapping("/addFoodLabel")
    @ClientAuth
    public ResponseResult addFoodLabel(@RequestParam("labelId") @NotNull(message = "分类不可为空") Integer labelId, @RequestParam("foodId") @NotNull(message = "食物不可为空") Integer foodId, HttpServletRequest request) {

        Integer userId = UserUtils.getUserId(request);

        boolean result = foodLabelService.addFoodToLabelVerifyUserId(labelId, foodId, userId);

        if (result) {
            return ResponseResult.success("添加成功");
        }

        return ResponseResult.failed(4000, "添加失败");
    }


    @GetMapping("/getShareFoodCardInfo")
    @ClientAuth
    public ResponseResult getShareFoodCardInfo(@RequestParam("cardId") @NotNull(message = "卡片不可为空") Integer cardId, HttpServletRequest request) {

        Optional<FoodCard> byIdAndShare = foodCardService.findByIdAndShare(cardId);

        if (byIdAndShare.isEmpty()) {
            Integer userId = UserUtils.getUserId(request);
            Optional<FoodCard> userShare = foodCardService.findUserCardById(cardId, userId);
            userShare.orElseThrow(() -> new ApiException("该卡片不公开哦"));
            return ResponseResult.success("获取成功", userShare.get());
        } else {
            return ResponseResult.success("获取成功", byIdAndShare.get());
        }


    }


    @GetMapping("/getUserFoodCards")
    public ResponseResult getUserFoodCards(@RequestParam("userId") Integer userId, @RequestParam(value = "pn", required = false, defaultValue = "1") @Min(value = 1, message = "超过最小范围") Integer pn, @RequestParam(value = "ps", required = false, defaultValue = "20") @Max(value = 20, message = "超过最大范围") Integer ps, HttpServletRequest request) {

        Integer uid = userUtils.getUserIdOrZero(request);
        IPage<FoodCard> foodCardIPage = foodCardService.findUserCardByUserIdPage(userId, uid, pn, ps);
        return tableResult("获取成功", foodCardIPage);
    }


    @GetMapping("/getUserFoodCardInfo")
    public ResponseResult getUserFoodCardInfo(@RequestParam("cardId") Integer cardId, HttpServletRequest request) {
        Integer uid = userUtils.getUserIdOrZero(request);
        Optional<FoodCard> foodCardIPage = foodCardService.findUserCardById(cardId, uid);
        return foodCardIPage.map(foodCard -> ResponseResult.success("获取成功", foodCard)).orElseGet(() -> ResponseResult.error(4004, "没有找到卡片"));

    }

    @GetMapping("/getUserFoodInfo")
    public ResponseResult getUserFoodInfo(@RequestParam("foodId") Integer foodId) {
        Optional<FoodInfo> UserFoodInfo = foodInfoService.findUserFoodInfoById(foodId);
        return UserFoodInfo.map(foodInfo -> ResponseResult.success("获取成功", foodInfo)).orElseGet(() -> ResponseResult.error(4004, "没有找到食物信息"));
    }


    @Data
    public static class UpdateFoodCard {
        @Getter
        private Integer cardId;
        @NotBlank(message = "标题不可为空")
        private String title;
        private String description;
        private String cover;
        private Integer schoolId;
        private Integer locationId;
        private Integer shareState;
    }

    //删除食物卡片
    @DeleteMapping("/deleteFoodCard")
    public ResponseResult deleteFoodCard(@RequestParam(name = "cardId") Integer cardId) {
        Boolean result = foodCardService.deleteFoodCard(cardId);
        if (!result) {
            return ResponseResult.error(5003, "删除失败");
        }
        return ResponseResult.success("删除成功");
    }


    @PostMapping("/updateFoodCardInfo")
    @ClientAuth
    public ResponseResult getUserFoodCardInfo(@RequestBody UpdateFoodCard updateFoodCard, HttpServletRequest request) {

        Integer uid = UserUtils.getUserId(request);
        Boolean result = foodCardService.updateById(uid, updateFoodCard);
        if (!result) {
            return ResponseResult.error(5003, "更新异常");
        }
        return ResponseResult.success("更新成功");

    }

    @Data
    public static class UpdateFoodInfo {
        @NotBlank(message = "名称不能为空")
        private String name;
        private Integer foodId;
        private String description;
        private String cover;
        private Integer price;
    }

    //修改用户信息
    @PostMapping("/updateFoodInfo")
    @ClientAuth
    public ResponseResult updateFoodInfo(@RequestBody UpdateFoodInfo updateFoodInfo) {
        Integer foodId = updateFoodInfo.getFoodId();
        Boolean result = foodInfoService.updateByFoodId(foodId, updateFoodInfo);
        if (!result) {
            return ResponseResult.error(5003, "更新异常");
        }
        return ResponseResult.success("更新成功");

    }

    //删除食物信息
    @DeleteMapping("/deleteFoodInfo")
    @ClientAuth
    public ResponseResult deleteFoodInfo(@RequestParam(name = "foodId") Integer foodId) {
        Boolean result = foodInfoService.deleteFoodInfo(foodId);
        if (!result) {
            return ResponseResult.error(5003, "删除失败");
        }
        return ResponseResult.success("删除成功");
    }


    //获得通过食堂Id查找食堂窗口信息
    @GetMapping("/getCanteenWindowInfo")
    public Map<String, Object> getCanteenWindowInfo(@RequestParam Integer canteen) {
        List<FoodWindow> foodCard = foodCardService.findCanteenWindowInfo(canteen);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("msg", "请求成功");
        result.put("item", foodCard);
        return result;
    }

    //获得通过窗口Id查找食堂窗口信息
    @GetMapping("/getWindowInfoByWindowId")
    public ResponseResult getWindowInfoByWindowId(@RequestParam Integer windowId) {
        Optional<FoodWindow> canteenInfo = foodCardService.findCanteenInfoById(windowId);

        return ResponseResult.success("获取成功", canteenInfo);
    }
    //获得窗口食物信息
    @GetMapping("/getCanteenWindowFoodInfo")
    public Map<String, Object> getCanteenWindowFoodInfo(@RequestParam Integer windowId) {
        List<FoodWindowInfo> WindowFoodInfo = foodInfoService.findFoodInfoById(windowId);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("msg", "请求成功");
        result.put("item", WindowFoodInfo);
        return result;
    }


    @Data
    public static class CollectionCard {
        private Integer collectionId;
        private Integer cardId;
        private Integer userId;
        @NotBlank(message = "标题不可为空")
        private String title;
        private String description;
        private String cover;
        private Integer collectionState;
    }

    //收藏卡片
    @ClientAuth
    @PostMapping("/collectionCard")
    public ResponseResult Collection(HttpServletRequest request, @RequestBody CollectionCard collectionCard) {
        Integer result = foodCardService.addCollectionCard(collectionCard);
        if (result < 0) return ResponseResult.failed(5000, "服务器错误");
        return ResponseResult.success("添加成功");
    }

//  获取收藏的卡片信息
    @GetMapping("/getCollectionCardInfo")
    public ResponseResult getCollectionCardInfo(@RequestParam Integer cardId,@RequestParam Integer userId) {
        Optional<UserCollection> collectionCardInfo = foodCardService.findCollectionCard(cardId, userId);
        return ResponseResult.success("获取成功", collectionCardInfo);
    }

//  修改收藏卡片的状态为0|1
    @ClientAuth
    @PostMapping("/updateCardCollectionState")
    public ResponseResult updateCollectionState(@RequestParam Integer cardId, @RequestParam Integer userId, @RequestParam Integer collectionState){
        foodCardService.updateCollectionState(cardId, userId, collectionState);
        return ResponseResult.success("修改成功");
    }

    //获得点赞量和收藏量
    @GetMapping("/getLikesCollection")
    public ResponseResult getLikesCollection(@RequestParam Integer cardId,@RequestParam Integer userId){
        Optional<CardLikeCollection> CardLikeCollection = foodCardService.getLikesCollection(cardId,userId);
        return ResponseResult.success("修改成功",CardLikeCollection);
    }

    //修改收藏量
    @ClientAuth
    @PostMapping("/updateCollection")
    public ResponseResult updateCollection(@RequestParam Integer cardId,@RequestParam Integer userId,@RequestParam Integer collection){
        foodCardService.updateCollection(cardId, userId, collection);
        return ResponseResult.success("修改成功");
    }


    //修改点赞状态
    @ClientAuth
    @PostMapping("/updateLikesState")
    public ResponseResult updateLikesState(@RequestParam Integer cardId,@RequestParam Integer userId,@RequestParam Integer likesState){
        foodCardService.updateLikesState(cardId, userId, likesState);
        return ResponseResult.success("修改成功");
    }
    //修改点赞量
    @ClientAuth
    @PostMapping("/updateLikes")
    public ResponseResult updateLikes(@RequestParam Integer cardId,@RequestParam Integer userId,@RequestParam Integer likes){
        foodCardService.updateLikes(cardId, userId, likes);
        return ResponseResult.success("修改成功");
    }

//    查询所有收藏信息
    @GetMapping("/getAllCollectionCard")
    public Map<String, Object> getCollectionCard(){
        List<UserCollection> CollectionCard = foodCardService.getAllCollectionCard();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("msg", "请求成功");
        result.put("item", CollectionCard);
        return result;
    }
}
