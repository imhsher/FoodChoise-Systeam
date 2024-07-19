package com.imcys.foodchoice.client.service.impl.auth;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imcys.foodchoice.client.dao.UserTokenMapper;
import com.imcys.foodchoice.client.model.auth.UserToken;
import com.imcys.foodchoice.client.model.user.UserInfo;
import com.imcys.foodchoice.client.service.auth.IUserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class UserTokenServiceImpl extends ServiceImpl<UserTokenMapper, UserToken> implements IUserTokenService {

    private final static String SYSTEM_CODE = "001";

    @Autowired
    private UserTokenMapper userTokenMapper;

    @Transactional
    public String addUserToken(UserInfo userInfo) {

        List<UserToken> userTokens = userTokenMapper.selectByUserId(userInfo.getId());

        if (userTokens.size() >= 5) {
            userTokenMapper.deleteById(userTokens.get(0));
        }

        UserToken userToken = new UserToken();
        userToken.setUserId(userInfo.getId());
        String token = generateUniqueToken();

        userToken.setToken(token);
        userTokenMapper.insert(userToken);
        return token;
    }

    @Override
    public String generateUniqueToken() {

        String token = SYSTEM_CODE + "-" + UUID.randomUUID();
        token = Base64.getEncoder().encodeToString(token.getBytes(StandardCharsets.UTF_8));
        return token;
    }

    @Override
    public UserToken getByToken(String token) {
        return userTokenMapper.selectByToken(token);
    }

}
