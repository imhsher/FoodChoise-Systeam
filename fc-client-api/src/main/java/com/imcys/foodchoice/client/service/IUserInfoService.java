package com.imcys.foodchoice.client.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imcys.foodchoice.client.controller.UserInfoController;
import com.imcys.foodchoice.client.model.user.UserAttention;
import com.imcys.foodchoice.client.model.user.UserInfo;
import com.imcys.foodchoice.client.model.user.UserInfoAttention;

import java.util.List;
import java.util.Optional;

public interface IUserInfoService extends IService<UserInfo> {

     Integer addUser(UserInfo userInfo);
     Integer registerUser(UserInfoController.RegUserInfo regUserInfo);

     UserInfo userLogin(UserInfoController.LoginInfo loginInfo);
     Optional<UserInfo> getOptById(Integer userId);


     //更新用户信息
    void updateUserInfo(UserInfo userInfo);

    void upAvatar(String headPic,Integer id);

    void loginOut(Integer userId);

    Optional<UserAttention> getAttentionInfo(Integer userId);

    void updateAttentionState(Integer userId, Integer attentionState, Integer attentionNum);

    List<UserAttention> shareAttentionUser();

    Optional<UserInfoAttention> shareNowAtt();

    void upPubAtt(Integer userId, Integer attentionNum, Integer attentionState);

    void addPriAttUserInfo(Integer userId, Integer attentionNum);

    Optional<UserInfoAttention> shareAtt(Integer userId);

    Optional<UserInfoAttention> shareAttUserInfo(Integer userId, Integer attId);

    void addUserAtt(Integer userId, Integer attId);

    void delUserAtt(Integer userId, Integer attId);

    List<UserInfoAttention> getAllUserInfo(Integer userId);

    void upPersonalityTags(String tags, Integer userId);
}
