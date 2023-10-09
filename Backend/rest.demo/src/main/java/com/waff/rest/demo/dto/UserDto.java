package com.waff.rest.demo.dto;

import com.waff.rest.demo.model.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class UserDto {


    private String id = UUID.randomUUID().toString();

    @Size(min=2, max=64)
    private String firstname;

    @Size(min=2, max=64)
    private String lastname;

    @Size(min=2, max=64)
    private String username;

    @Email
    @Column(name = "email_address")
    private String email;

    @NotNull
    @Column(name = "user_type")
    private UserType userType;

    @Size(min=2, max=64)
    private String password;

    private boolean enabled = true;

    public UserDto() {
        this.userType = UserType.admin;
    }

    public String getId() {
        return id;
    }

    public UserDto setId(String id) {
        if(StringUtils.isNotBlank(id)) {
            this.id = id;
        }
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public UserDto setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public UserDto setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserType getUserType() {
        return userType;
    }

    public UserDto setUserType(UserType userType) {
        this.userType = userType;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public UserDto setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    // tostring
    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", userType=" + userType +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
