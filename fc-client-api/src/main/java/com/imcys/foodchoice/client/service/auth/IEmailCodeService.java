package com.imcys.foodchoice.client.service.auth;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imcys.foodchoice.client.model.auth.EmailCode;

public interface IEmailCodeService extends IService<EmailCode> {


    void send(String toEmail, String mailTitle, String postContent);

    void sendMailHtml(String toEmail, String mailTitle, String html);


    void sendRegMail(String email);

    EmailCode getByEmail(String email);

    public boolean isVerificationAllowed(String email);
}
