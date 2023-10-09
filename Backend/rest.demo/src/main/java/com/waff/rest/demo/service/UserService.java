package com.waff.rest.demo.service;

import java.util.List;
import java.util.Optional;

import com.waff.rest.demo.model.UserType;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.waff.rest.demo.model.User;
import com.waff.rest.demo.repository.UserRepository;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * Get all users in the database.
     * @return all users in the database.
     */
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Get all user by giving userType.
     * @param usertype usertype
     * @return all user by giving userType.
     */
    public List<User> getUserByUserType(UserType usertype) {
        return userRepository.findByUserType(usertype);
    }


    /**
     * Get user by giving userId.
     * @param id userId
     * @return user by giving userId.
     */
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    /**
     * Check if  user by giving userId exist.
     * @param id userId
     * @return user by giving userId.
     */
    public boolean existUserById(String id) {
        return userRepository.existsById(id);
    }

    /**
     * Create new User
     * @param user user
     * @return the new created user
     */
    public Optional<User> createUser(@Valid User user) {
        if (!userRepository.existsUserByUsername(user.getUsername())) {
            String password = user.getPassword();
            user.setPassword(passwordEncoder.encode(password));
            User saved = userRepository.save(user);
            return Optional.of(saved);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Update an existing User by giving userId and new content of the user
     * @param user  user to update
     * @return updated user
     */
    public Optional<User> updateUser(@Valid User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        if(existingUser != null) {
            User saved = userRepository.save(user);
            return Optional.of(saved);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Delete User by giving userId
     * @param id userId
     */
    public boolean deleteUserById(@NotBlank String id) {
        User existingUser = userRepository.findById(id).orElse(null);
        if(existingUser != null) {
            userRepository.deleteById(id);
            return true;
        } else {
           return false;
        }
    }

    /**
     * Delete all user.
     */
    public void deleteUsers() {
        userRepository.deleteAll();
    }

    /**
     * Check if database has user.
     */
    public boolean hasUsers() {
        return userRepository.count() > 0L;
    }


    public User getUserByUsername(String string) {
        return userRepository.findUserByUsername(string);
    }
}
