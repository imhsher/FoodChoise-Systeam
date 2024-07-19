package com.imcys.foodchoice.client.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imcys.foodchoice.client.model.auth.UserToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserTokenMapper extends BaseMapper<UserToken> {

    @Select("select * from fc_user_token where user_id = #{userId}")
    List<UserToken> selectByUserId(Integer userId);

    @Select("select * from fc_user_token where token = #{token}")
    UserToken selectByToken(String token);
}
