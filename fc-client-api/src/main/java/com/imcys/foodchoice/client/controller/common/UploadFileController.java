package com.imcys.foodchoice.client.controller.common;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.imcys.foodchoice.client.ann.ClientAuth;
import com.imcys.foodchoice.common.core.controller.BaseController;
import com.imcys.foodchoice.common.exception.ApiException;
import com.imcys.foodchoice.common.model.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
//@ClientAuth
@RequestMapping("api/v1/client/file")
public class UploadFileController extends BaseController {
    @Value("${foodchoice.file-upload-path}")
    private String fileUploadPath;

    @PostMapping("/uploadCoverImage")
    public ResponseResult uploadCoverImage(@RequestParam MultipartFile file) throws IOException {
        //获取文件原始名称
        String originalFilename = file.getOriginalFilename();
        //获取文件的类型
        String type = FileUtil.extName(originalFilename);
        log.info("文件类型是：" + type);
        if (type == null) throw new ApiException("上传文件类型异常");
        if (!type.equals("png") && !type.equals("jpge") && !type.equals("jpg") && !type.equals("webp"))
            throw new ApiException("上传文件类型异常");

        //获取文件
        File uploadParentFile = new File(fileUploadPath);
        //判断文件目录是否存在
        if (!uploadParentFile.exists()) {
            //如果不存在就创建文件夹
            uploadParentFile.mkdirs();
        }
        //定义一个文件唯一标识码（UUID）
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + StrUtil.DOT + type;

        File uploadFile = new File(fileUploadPath + fileName);
        //将临时文件转存到指定磁盘位置
        file.transferTo(uploadFile);

        Map<String,String> result = new HashMap<>();
        result.put("fileName",fileName);
        result.put("fileId",uuid);

        return ResponseResult.success("获取成功",result);

    }
}
