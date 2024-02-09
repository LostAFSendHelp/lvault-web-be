package com.example.lostaf.lvaultweb.utils;

import java.util.regex.Pattern;

public class StringUtils {
    private static final String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9 ]*$";

    private static final Pattern ALPHANUMERIC_PATTERN = Pattern.compile(ALPHANUMERIC_REGEX);

    public static boolean isAlphanumeric(String text) {
        return ALPHANUMERIC_PATTERN.matcher(text).matches();
    }
}
