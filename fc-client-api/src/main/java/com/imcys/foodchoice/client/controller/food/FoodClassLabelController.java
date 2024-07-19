package com.imcys.foodchoice.client.controller.food;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imcys.foodchoice.client.ann.ClientAuth;
import com.imcys.foodchoice.client.service.food.IFoodClassService;
import com.imcys.foodchoice.client.service.food.IFoodLabelService;
import com.imcys.foodchoice.client.utils.UserUtils;
import com.imcys.foodchoice.common.core.controller.BaseController;
import com.imcys.foodchoice.common.model.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("api/v1/client/food")
@ClientAuth
public class FoodClassLabelController extends BaseController {

    @Autowired
    private IFoodClassService foodClassService;
    @Autowired
    private IFoodLabelService foodLabelService;


    @Data
    public static class NewFoodClass {
        @NotBlank(message = "分类名称不可为空")
        private String name;
        @NotBlank(message = "卡片ID不可为空")
        private Integer cardId;
        private Integer userId;
    }

    @PostMapping("/addFoodClassInfo")
    public ResponseResult addFoodClass(HttpServletRequest request, @RequestBody NewFoodClass newFoodClass) {

        newFoodClass.setUserId(UserUtils.getUserId(request));

        if (!foodClassService.addFoodClass(newFoodClass)) {
            return ResponseResult.failed(5003, "添加异常");
        } else {
            return ResponseResult.success("新增成功");
        }


    }

    @Data
    public static class NewFoodLabel {
        @NotBlank(message = "分类名称不可为空")
        private String name;
        @NotBlank(message = "卡片ID不可为空")
        private Integer cardId;
        private Integer userId;
    }

    @PostMapping("/addFoodLabelInfo")
    public ResponseResult addFoodLabel(HttpServletRequest request, @RequestBody NewFoodLabel newFoodLabel) {

        newFoodLabel.setUserId(UserUtils.getUserId(request));

        if (!foodLabelService.addFoodLabel(newFoodLabel)) {
            return ResponseResult.failed(5003, "添加异常");
        } else {
            return ResponseResult.success("新增成功");
        }

    }

}
