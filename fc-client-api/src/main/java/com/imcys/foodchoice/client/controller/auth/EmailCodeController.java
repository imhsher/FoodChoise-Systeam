package com.imcys.foodchoice.client.controller.auth;

import com.imcys.foodchoice.client.service.auth.IEmailCodeService;
import com.imcys.foodchoice.common.core.controller.BaseController;
import com.imcys.foodchoice.common.model.ResponseResult;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/client/user")
@Validated
public class EmailCodeController extends BaseController {

    private final IEmailCodeService emailCodeService;

    public EmailCodeController(IEmailCodeService emailCodeService) {
        this.emailCodeService = emailCodeService;
    }


    /**
     * 获取验证码邮箱邮箱
     */
    @Data
    static class RegEmailCode {
        @NotBlank(message = "绑定邮箱不可为空")
        private String email;
    }

    @PostMapping("/getRegEmailCode")
    public ResponseResult getRegEmail(@RequestBody RegEmailCode regEmailCode) {

        String email = regEmailCode.getEmail();
        //检验是否发送
        if (emailCodeService.isVerificationAllowed(email)) {
            emailCodeService.sendRegMail(email);
            return ResponseResult.success("发送成功");
        } else {
            return ResponseResult.failed(4003, "发信太快，请耐心等待");
        }

    }
}
