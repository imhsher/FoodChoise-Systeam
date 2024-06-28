package com.imcys.foodchoice.client.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imcys.foodchoice.client.controller.UserInfoController;
import com.imcys.foodchoice.client.dao.EmailCodeMapper;
import com.imcys.foodchoice.client.dao.UserAttentionMapper;
import com.imcys.foodchoice.client.dao.UserInfoAttentionMapper;
import com.imcys.foodchoice.client.dao.UserInfoMapper;
import com.imcys.foodchoice.client.model.auth.EmailCode;
import com.imcys.foodchoice.client.model.user.UserAttention;
import com.imcys.foodchoice.client.model.user.UserInfo;
import com.imcys.foodchoice.client.model.user.UserInfoAttention;
import com.imcys.foodchoice.client.service.IUserInfoService;
import com.imcys.foodchoice.client.utils.ValidateCodeUtils;
import com.imcys.foodchoice.common.exception.ApiException;
import com.imcys.foodchoice.common.utils.StringUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imcys.foodchoice.common.utils.PasswordUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {


    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private EmailCodeMapper emailCodeMapper;

    @Autowired
    private UserInfoAttentionMapper userInfoAttentionMapper;

    @Autowired
    private UserAttentionMapper userAttentionMapper;

    @Autowired
    private HttpSession httpSession; // 通过依赖注入获取会话对象


    private PasswordUtils passwordUtils = new PasswordUtils();

    @Override
    public Integer addUser(UserInfo userInfo) {
        return userInfoMapper.insert(userInfo);
    }

    @Override
    @Transactional
    public Integer registerUser(UserInfoController.RegUserInfo regUserInfo) {

        validateCaptchaCode(regUserInfo.getCaptchaCode());

        validateEmailCode(regUserInfo.getEmail(), regUserInfo.getEmailCode());

        if (userInfoMapper.selectByEmailOptional(regUserInfo.getEmail()).isPresent()) {
            throw new ApiException("邮箱已经被注册了");
        }
        if (userInfoMapper.selectByNameOptional(regUserInfo.getUsername()).isPresent()) {
            throw new ApiException("用户名已被占用");
        }


        String mPassword = passwordUtils.encode(regUserInfo.getPassword());
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(regUserInfo.getEmail());
        userInfo.setPassword(mPassword);
        userInfo.setUsername(regUserInfo.getUsername());
        userInfo.setNickname(regUserInfo.getUsername());

//        Integer id = userInfo.getId();
//
//        UserInfoAttention userInfoAttention = new UserInfoAttention();
//        userInfoAttention.setUserId(id);
//
//        userInfoAttentionMapper.insert(userInfoAttention);
        emailCodeMapper.updateStateByEmail(regUserInfo.getEmail(), 1);

        return addUser(userInfo);

    }

    @Override
    public UserInfo userLogin(UserInfoController.LoginInfo loginInfo) {

        if (StringUtils.isEmpty(loginInfo.getUsername()) && StringUtils.isEmpty(loginInfo.getEmail())) {
            throw new ApiException("请填写用户名");
        }

        if (StringUtils.isEmpty(loginInfo.getEmail())) {
            //用户名和密码同时检查
            loginInfo.setEmail(loginInfo.getUsername());
        }

        if (loginInfo.getUsername() == null && loginInfo.getEmail() == null) {
            throw new ApiException("请输入用户名/邮箱");
        }

        UserInfo userInfo = userInfoMapper.selectByNameOrEmail(loginInfo);

        if (userInfo == null) {
            throw new ApiException("用户不存在");
        }

        if (!passwordUtils.matches(loginInfo.getPassword(), userInfo.getPassword())) {
            throw new ApiException("用户名或密码错误");
        }

        return userInfo;
    }

    @Override
    public Optional<UserInfo> getOptById(Integer userId) {
        return userInfoMapper.selectOptById(userId);
    }

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        userInfo.setUpdateDate(LocalDateTime.now());
        userInfoMapper.updateUserInfo(userInfo);
    }

    @Override
    public void upAvatar(String headPic,Integer id) {
        UserInfo userInfo = userInfoMapper.selectById(id);

        userInfo.setUpdateDate(LocalDateTime.now());
        userInfoMapper.upAvatar(headPic, LocalDateTime.now(), id);
    }

    @Override
    public void loginOut(Integer userId) {
        userInfoMapper.loginOut(userId);
    }

    @Override
    public Optional<UserAttention> getAttentionInfo(Integer userId) {
        return userInfoMapper.shareAttentionInfo(userId);
    }

    @Override
    public void updateAttentionState(Integer userId, Integer attentionState, Integer attentionNum) {
        userInfoMapper.updateAttentionState(userId, attentionState, attentionNum);
    }

    @Override
    public List<UserAttention> shareAttentionUser() {
        return userInfoMapper.shareAttentionUser();
    }

    @Override
    public Optional<UserInfoAttention> shareNowAtt() {
        return userInfoMapper.shareNowAtt();
    }

    @Override
    public void upPubAtt(Integer userId, Integer attentionNum, Integer attentionState) {
        userInfoMapper.upPubAtt(userId,attentionNum,attentionState);
    }

    @Override
    public void addPriAttUserInfo(Integer userId, Integer attentionNum) {
        UserAttention userAttention = new UserAttention();
        userAttention.setUserId(userId);
        userAttention.setAttentionNum(attentionNum);
        userAttention.setAttentionState(1);
        userAttentionMapper.insert(userAttention);
    }

    @Override
    public Optional<UserInfoAttention> shareAtt(Integer userId) {
        return userInfoMapper.getAllAttention(userId);
    }

    @Override
    public Optional<UserInfoAttention> shareAttUserInfo(Integer userId, Integer attId) {
        return userInfoMapper.getAttUserInfo(userId, attId);
    }

    @Override
    public void addUserAtt(Integer userId, Integer attId) {
        UserInfoAttention userInfoAttention = new UserInfoAttention();
        userInfoAttention.setUserId(userId);
        userInfoAttention.setAttId(attId);
        userInfoAttentionMapper.insert(userInfoAttention);
    }

    @Override
    public void delUserAtt(Integer userId, Integer attId) {
        userInfoMapper.delUserAtt(userId, attId);
    }

    @Override
    public List<UserInfoAttention> getAllUserInfo(Integer userId) {
        return userInfoMapper.getAttUser(userId);
    }

    @Override
    public void upPersonalityTags(String tags, Integer userId) {
        userInfoMapper.upTags(tags, userId);
    }

    private void validateEmailCode(String email, String code) {

        EmailCode emailCode = emailCodeMapper.selectByEmail(email);

        if (emailCode == null) throw new ApiException("邮箱验证码错误");

        LocalDateTime thisDate = LocalDateTime.now();

        if (thisDate.isAfter(emailCode.getEndTime())) throw new ApiException("邮箱验证码过期");

        if (!emailCode.getCode().equals(code) && emailCode.getState() != 0) throw new ApiException("邮箱验证码错误");


    }

    private void validateCaptchaCode(String captchaCode) {


        Object session = httpSession.getAttribute(ValidateCodeUtils.sessionKey);

        if (StringUtils.isEmpty(session)) {
            throw new ApiException("验证码错误");
        }

        if (!session.toString().equalsIgnoreCase(captchaCode)) {
            throw new ApiException("验证码错误");
        }
    }


}
