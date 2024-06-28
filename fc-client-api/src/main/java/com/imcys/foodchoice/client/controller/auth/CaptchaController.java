package com.imcys.foodchoice.client.controller.auth;

import com.imcys.foodchoice.client.utils.ValidateCodeUtils;
import com.imcys.foodchoice.common.core.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/client/captcha")
public class CaptchaController extends BaseController {

    // 生成验证码图片
    @GetMapping("/getCaptchaImage")
    @ResponseBody
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/png");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Expire", "0");
            response.setHeader("Pragma", "no-cache");
            ValidateCodeUtils validateCode = new ValidateCodeUtils();
            // 直接返回图片
            validateCode.getRandomCodeImage(request, response);
        } catch (Exception e) {
            log.error("验证码异常", e);
        }

    }
}
