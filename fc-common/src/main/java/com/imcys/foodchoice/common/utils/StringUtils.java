package com.imcys.foodchoice.common.utils;

public class StringUtils {
    public static boolean isEmpty(Object str){
        return str == null || String.valueOf(str).isEmpty();
    }
}
