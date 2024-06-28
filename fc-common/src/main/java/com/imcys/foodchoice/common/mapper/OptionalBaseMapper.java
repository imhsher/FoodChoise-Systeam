package com.imcys.foodchoice.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.io.Serializable;
import java.util.Optional;

public interface OptionalBaseMapper<T> extends BaseMapper<T> {
    default Optional<T> selectByIdOptional(Serializable id) {
        T result = selectById(id);
        return Optional.ofNullable(result);
    }
}
