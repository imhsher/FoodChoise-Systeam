package com.imcys.foodchoice.client.utils;

import com.imcys.foodchoice.client.model.auth.UserToken;
import com.imcys.foodchoice.client.service.auth.IUserTokenService;
import com.imcys.foodchoice.common.exception.ApiException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserUtils {

    @Autowired
    private  IUserTokenService userTokenService;

    public static Integer getUserId(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        if (userId == null) throw new ApiException("尚未登录");
        return userId;
    }

    public  Integer getUserIdOrZero(HttpServletRequest request) {
        Integer uid = 0;
        List<Cookie> cookies = Arrays.stream(request.getCookies()).filter(item -> item.getName().equals("sx_token")).toList();

        if (!cookies.isEmpty()) {
            String xfToken = cookies.get(0).getValue();
            UserToken userToken = userTokenService.getByToken(xfToken);
            if (userToken != null) {
                uid = userToken.getUserId();
            }
        }
        return uid;
    }

}
