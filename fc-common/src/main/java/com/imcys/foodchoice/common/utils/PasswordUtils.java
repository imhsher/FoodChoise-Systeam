package com.imcys.foodchoice.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.util.DigestUtils.md5DigestAsHex;

public class PasswordUtils {
    private static String topSalt = "食";

    private static String endSalt = "选";

    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }


}
