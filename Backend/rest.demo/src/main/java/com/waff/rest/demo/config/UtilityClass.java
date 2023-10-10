package com.waff.rest.demo.config;

public class UtilityClass {
    public static boolean isBCryptHash(String encodedPassword) {
        // Checks if the encoded password starts with the BCrypt identifier "$2a$"
        return encodedPassword.startsWith("$2a$");
    }
}
