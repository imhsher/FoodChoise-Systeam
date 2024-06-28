package com.imcys.foodchoice.client.controller.food;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imcys.foodchoice.client.ann.ClientAuth;
import com.imcys.foodchoice.client.model.food.FoodCard;
import com.imcys.foodchoice.client.service.food.IFoodCardService;
import com.imcys.foodchoice.common.core.controller.BaseController;
import com.imcys.foodchoice.common.enu.SearchType;
import com.imcys.foodchoice.common.exception.ApiException;
import com.imcys.foodchoice.common.model.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/client/food")
@Validated
public class HomeFoodController extends BaseController {


    @Autowired
    private IFoodCardService foodCardService;

    @GetMapping("/getRecentlyShareCard")
    public ResponseResult getRecentlyShare(@RequestParam(value = "pn", required = false, defaultValue = "1") @Min(value = 1, message = "超过最小范围") Integer pn, @RequestParam(value = "ps", required = false, defaultValue = "30") @Max(value = 30, message = "超过最大范围") Integer ps) {
        IPage<FoodCard> foodCardIPage = foodCardService.findByShare(pn, ps);
        return tableResult("获取成功", foodCardIPage);
    }

    @Data
    public class SearchInfo {
        private SearchType searchType = SearchType.FOOD_CARD;
        private String keyword = "";
        private Integer pn = 1;
        private Integer ps = 20;
    }




}
