package com.imcys.foodchoice.client.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imcys.foodchoice.client.model.auth.EmailCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Optional;

@Mapper
public interface EmailCodeMapper extends BaseMapper<EmailCode> {

    @Select("SELECT * FROM fc_email_code where email = #{email}")
    public EmailCode selectByEmail(String email);

    @Select("SELECT * FROM fc_email_code where email = #{email}")
    public Optional<EmailCode> selectByEmailOptional(String email);

    @Update("UPDATE fc_email_code set state = #{state} where email = #{email}")
    public Integer updateStateByEmail(@Param("email") String email, @Param("state") Integer state);
}
