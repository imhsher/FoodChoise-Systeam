package com.imcys.foodchoice.client.service.auth;

import com.imcys.foodchoice.client.model.auth.UserToken;
import com.imcys.foodchoice.client.model.user.UserInfo;

public interface IUserTokenService {
    public String addUserToken(UserInfo userInfo);
    String generateUniqueToken();

    UserToken getByToken(String token);
}
