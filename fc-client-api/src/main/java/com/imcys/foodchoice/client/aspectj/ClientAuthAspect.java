package com.imcys.foodchoice.client.aspectj;


import com.imcys.foodchoice.client.ann.ClientAuth;
import com.imcys.foodchoice.client.model.auth.UserToken;
import com.imcys.foodchoice.client.service.auth.IUserTokenService;
import com.imcys.foodchoice.common.exception.ApiException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class ClientAuthAspect {


    @Autowired
    private IUserTokenService userTokenService;

    @Before("@within(com.imcys.foodchoice.client.ann.ClientAuth)")
    public void allFunctionAuth(JoinPoint joinPoint) {

        String methodName = joinPoint.getSignature().getName();

        Class<?> targetClass = joinPoint.getTarget().getClass();

        //获取方法上的注解 注意由于AOP检验，因此注解必然存在
        ClientAuth clientAuth = targetClass.getAnnotation(ClientAuth.class);

        for (String s : clientAuth.exclude()) {
            if (methodName.equals(s)) return;
        }

        loginVerification();
    }

    @Before("@annotation(com.imcys.foodchoice.client.ann.ClientAuth)")
    public void thisLoginAuth() {
        loginVerification();
    }

    private void loginVerification() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) throw new ApiException(4003, "请求错误");

        HttpServletRequest request = attributes.getRequest();

        if (request.getCookies() == null) throw new ApiException(4003, "请先完成登录");


        List<Cookie> cookies = Arrays.stream(request.getCookies()).filter(item -> item.getName().equals("sx_token")).toList();

        if (cookies.isEmpty()) throw new ApiException(4003, "请先完成登录");

        String xfToken = cookies.get(0).getValue();

        UserToken userToken = userTokenService.getByToken(xfToken);
        if (userToken == null) throw new ApiException(4003, "请先完成登录");

        request.setAttribute("userId", userToken.getUserId());
    }


}
