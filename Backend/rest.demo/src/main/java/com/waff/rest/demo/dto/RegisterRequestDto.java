package com.waff.rest.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class RegisterRequestDto {

    @Size(min=2, max=64)
    private String firstname;

    @Size(min=2, max=64)
    private String lastname;

    @Size(min=2, max=64)
    private String username;

    @Email
    private String email;

    @Size(min=2, max=64)
    private String password;

    public RegisterRequestDto() {
    }

    public String getFirstname() {
        return firstname;
    }

    public RegisterRequestDto setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public RegisterRequestDto setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public RegisterRequestDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RegisterRequestDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterRequestDto setPassword(String password) {
        this.password = password;
        return this;
    }
}
