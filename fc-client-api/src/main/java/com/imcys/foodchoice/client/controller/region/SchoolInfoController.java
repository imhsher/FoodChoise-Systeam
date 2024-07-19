package com.imcys.foodchoice.client.controller.region;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imcys.foodchoice.client.ann.ClientAuth;
import com.imcys.foodchoice.client.model.region.RegionSchool;
import com.imcys.foodchoice.client.service.IRegionSchoolService;
import com.imcys.foodchoice.common.core.controller.BaseController;
import com.imcys.foodchoice.common.model.ResponseResult;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/client")
@Validated
public class SchoolInfoController extends BaseController {

    @Autowired
    IRegionSchoolService regionSchoolService;

    @GetMapping("/getSchoolList")
    public ResponseResult getSchoolAll(@Min(value = 1, message = "超过最小范围") Integer pn, @Max(value = 30, message = "超过最大范围") Integer ps, String name) {
        IPage<RegionSchool> schoolAll = regionSchoolService.getSchoolByName(pn, ps, name);
        return tableResult("获取成功", schoolAll);
    }
}
