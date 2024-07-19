package com.imcys.foodchoice.client.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imcys.foodchoice.client.controller.UserInfoController;
import com.imcys.foodchoice.client.model.user.UserAttention;
import com.imcys.foodchoice.client.model.user.UserInfo;
import com.imcys.foodchoice.client.model.user.UserInfoAttention;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    @Select("select * from fc_user_info where email = #{email}")
    UserInfo selectByEmail(String email);

    @Select("select * from fc_user_info where email = #{email}")
    Optional<UserInfo> selectByEmailOptional(String email);

    @Select("select * from fc_user_info where username = #{username}")
    UserInfo selectByName(String email);

    @Select("select * from fc_user_info where email = #{email}")
    Optional<UserInfo> selectByNameOptional(String email);

    UserInfo selectByNameOrEmail(UserInfoController.LoginInfo loginInfo);

    Optional<UserInfo> selectOptById(Integer userId);

    @Update("update fc_user_info set nickname=#{nickname},phone=#{phone},update_date=#{updateDate} where id=#{id}")
    void updateUserInfo(UserInfo userInfo);

    @Update("update fc_user_info set head_pic=#{headPic},update_date=#{updateDate} where id=#{id}")
    void upAvatar(@Param("headPic")String headPic, @Param("updateDate")LocalDateTime updateDate, @Param("id")Integer id);

    @Delete("delete from fc_user_token where user_id=#{userId}")
    void loginOut(@Param("userId") Integer userId);

    @Select("select * from fc_user_attention where user_id=#{userId}")
    Optional<UserAttention> shareAttentionInfo(@Param("userId")Integer userId);

    @Update("update fc_user_attention set attention_state =#{attentionState} , attention_num =#{attentionNum} where user_id=#{userId}")
    void updateAttentionState(@Param("userId")Integer userId, @Param("attentionState")Integer attentionState, @Param("attentionNum")Integer attentionNum);

    @Select("select * from fc_user_attention where attention_state = 1")
    List<UserAttention> shareAttentionUser();

    @Select("select * from fc_user_info_attention where attention_state = 0 ORDER BY RAND() LIMIT 1")
    Optional<UserInfoAttention> shareNowAtt();

    @Update("update fc_user_info_attention set  attention_num =#{attentionNum} , attention_state =#{attentionState} where user_id=#{userId}")
    void upPubAtt(@Param("userId")Integer userId, @Param("attentionNum")Integer attentionNum, @Param("attentionState")Integer attentionState);

    @Select("select * from fc_user_info_attention where user_id =#{userId}")
    Optional<UserInfoAttention> getAllAttention(@Param("userId")Integer userId);

    @Select("select * from fc_user_info_attention where user_id =#{userId} and att_id =#{attId}")
    Optional<UserInfoAttention> getAttUserInfo(@Param("userId")Integer userId, @Param("attId")Integer attId);

    @Delete("delete from fc_user_info_attention where user_id =#{userId} and att_id =#{attId}")
    void delUserAtt(@Param("userId")Integer userId, @Param("attId")Integer attId);

    @Select("select * from fc_user_info_attention where user_id = #{userId}")
    List<UserInfoAttention> getAttUser(Integer userId);

    @Update("update fc_user_info set personality_tags=#{tags} where id=#{userId}")
    void upTags(@Param("tags") String tags, @Param("userId") Integer userId);
}
