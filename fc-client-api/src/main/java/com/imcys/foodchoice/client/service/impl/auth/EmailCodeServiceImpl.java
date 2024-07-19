package com.imcys.foodchoice.client.service.impl.auth;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imcys.foodchoice.client.dao.EmailCodeMapper;
import com.imcys.foodchoice.client.model.auth.EmailCode;
import com.imcys.foodchoice.client.service.auth.IEmailCodeService;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.sql.Date;
import java.time.LocalDateTime;

@Slf4j
@Service
public class EmailCodeServiceImpl extends ServiceImpl<EmailCodeMapper, EmailCode> implements IEmailCodeService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailCodeMapper emailCodeMapper;


    @Override
    public void send(String toEmail, String mailTitle, String postContent) {

        SimpleMailMessage message = new SimpleMailMessage();
        //邮件设置
        message.setSubject(mailTitle);
        message.setText(postContent);
        message.setTo(toEmail);
        message.setFrom("3248841453@qq.com");
        javaMailSender.send(message);

    }

    @Override
    public void sendMailHtml(String toEmail, String mailTitle, String html) {
        //获取MimeMessage
        //面向对象的多态，javaMailSender.createMimeMessage()，多用途的网际邮件扩充协议
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper = new MimeMessageHelper(message, true);
            //邮件发送人
            mimeMessageHelper.setFrom("3248841453@qq.com");
            //邮件接收人
            mimeMessageHelper.setTo(toEmail);
            //邮件主题
            mimeMessageHelper.setSubject(mailTitle);
            //邮件内容,HTML格式
            mimeMessageHelper.setText(html, true);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void sendRegMail(String email) {
        EmailCode emailCode = getByEmail(email);

        // 基础信息
        LocalDateTime thisDate = LocalDateTime.now();
        String token = DigestUtils.md5DigestAsHex((email + thisDate.getSecond() + "SX").getBytes());
        String code = String.valueOf(RandomUtil.randomInt(1000, 9999));

        // 更新数据
        EmailCode upEmailCode = new EmailCode();
        upEmailCode.setEmail(email);
        upEmailCode.setCreateTime(thisDate);
        upEmailCode.setEndTime(thisDate.plusMinutes(30));
        upEmailCode.setTag(0);
        upEmailCode.setAuthCode(token);
        upEmailCode.setCode(code);

        if (emailCode != null) {
            UpdateWrapper<EmailCode> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", emailCode.getId());
            emailCodeMapper.update(upEmailCode, updateWrapper);
        } else {
            emailCodeMapper.insert(upEmailCode);
        }

        sendMailHtml(email, "食选注册邮箱验证", buildContent(code));


    }

    @Override
    public EmailCode getByEmail(String email) {
        return emailCodeMapper.selectByEmail(email);
    }

    @Override
    public boolean isVerificationAllowed(String email) {

        EmailCode emailCode = getByEmail(email);

        // 没有早期记录
        if (emailCode == null) return true;

        LocalDateTime thisTime = LocalDateTime.now();
        LocalDateTime createLocalDate = emailCode.getCreateTime();
        //已经超过发送间隔就可以发送，反之不行
        return thisTime.isAfter(createLocalDate.plusMinutes(1));
    }


    /**
     * 读取邮件模板
     * 替换模板中的信息
     *
     * @param context 内容
     * @return
     */
    public String buildContent(String context) {
        //加载邮件html模板
        ClassPathResource resource = new ClassPathResource("templates/view/regMailtemplate.html");
        InputStream inputStream = null;
        BufferedReader fileReader = null;
        StringBuilder buffer = new StringBuilder();
        String line;
        try {
            inputStream = resource.getInputStream();
            fileReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            log.info("发送邮件读取模板失败{}", e);
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //替换html模板中的参数
        return MessageFormat.format(buffer.toString(), context);
    }


}
