package com.waff.rest.demo.config;

public class UtilityClass {
    public static boolean isBCryptHash(String encodedPassword) {
        return encodedPassword.startsWith("$2a$");
    }
}
