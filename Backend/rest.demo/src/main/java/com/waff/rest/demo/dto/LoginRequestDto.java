package com.waff.rest.demo.dto;

public class LoginRequestDto {
    private String username;
    private String password;

    // DEFAULT CONSTRUCTOR
    public LoginRequestDto() {
    }

    // GETTER AND SETTER
    public String getUsername() {
        return username;
    }

    public LoginRequestDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginRequestDto setPassword(String password) {
        this.password = password;
        return this;
    }
}
