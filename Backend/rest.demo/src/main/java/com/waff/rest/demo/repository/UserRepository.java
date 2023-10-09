package com.waff.rest.demo.repository;

import java.util.List;

import com.waff.rest.demo.model.UserType;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import com.waff.rest.demo.model.User;

public interface UserRepository extends JpaRepository<User, String> {
    List<User> findByUserType(@NotBlank UserType userType);

    User findUserByUsername(String username);
    boolean existsUserByUsername(String username);
    User findByEmail(String email);
}
