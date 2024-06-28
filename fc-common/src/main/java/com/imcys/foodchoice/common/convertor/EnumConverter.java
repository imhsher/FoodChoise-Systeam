package com.imcys.foodchoice.common.convertor;

import org.springframework.core.convert.converter.Converter;

public class EnumConverter<T extends Enum<T>> implements Converter<String, T> {

    private final Class<T> enumType;

    public EnumConverter(Class<T> enumType) {
        this.enumType = enumType;
    }

    @Override
    public T convert(String source) {
        return Enum.valueOf(enumType, source.toUpperCase());
    }
}
