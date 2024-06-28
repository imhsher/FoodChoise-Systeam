package com.imcys.foodchoice.common.core.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imcys.foodchoice.common.core.model.PageDataModel;
import com.imcys.foodchoice.common.model.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

    protected ResponseResult tableResult(String msg, IPage page) {
        PageDataModel pageDataModel = new PageDataModel();
        pageDataModel.setItem(page.getRecords());
        pageDataModel.setSize(page.getSize());
        pageDataModel.setCurrent(page.getCurrent());
        pageDataModel.setMore(page.getCurrent() != page.getPages());
        pageDataModel.setPages(page.getPages());

        return ResponseResult.success(msg, pageDataModel);
    }

}
