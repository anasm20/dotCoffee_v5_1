package com.waff.rest.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", nullable = false)
    private String id;

    @Size(min=2, max=64)
    @Column(name = "first_name")
    private String firstname;

    @Size(min=2, max=64)
    @Column(name = "last_name")
    private String lastname;

    @Size(min=2, max=64)
    @Column(name = "username", unique = true)
    private String username;

    @Email
    @Column(name = "email_address", unique = true)
    private String email;

    @Column(name = "role", nullable = true)
    @Enumerated(EnumType.STRING)
    private UserRole userType;

    @Size(min=2, max=64)
    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    private String jwt;

    public User() {
        userType = UserRole.USER;
    }

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public User setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public User setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserRole getUserType() {
        return userType;
    }

    public User setUserType(UserRole userType) {
        this.userType = userType;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getJwt() {
        return jwt;
    }

    public User setJwt(String jwt) {
        this.jwt = jwt;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public User setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}
