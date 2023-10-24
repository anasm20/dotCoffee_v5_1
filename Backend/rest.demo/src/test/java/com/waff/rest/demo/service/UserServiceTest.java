package com.waff.rest.demo.service;

import com.waff.rest.demo.model.User;
import com.waff.rest.demo.model.UserRole;
import com.waff.rest.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUsers() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        assertTrue(userService.getUsers().isEmpty());
    }

    @Test
    void getUserByUserType() {
        when(userRepository.findByUserType(UserRole.USER)).thenReturn(Collections.emptyList());
        assertTrue(userService.getUserByUserType(UserRole.USER).isEmpty());
    }

    @Test
    void getUserById() {
        when(userRepository.findById("1")).thenReturn(Optional.of(new User()));
        assertTrue(userService.getUserById("1").isPresent());
    }

    @Test
    void existUserById() {
        when(userRepository.existsById("1")).thenReturn(true);
        assertTrue(userService.existUserById("1"));
    }

    @Test
    void createUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        when(userRepository.existsUserByUsername(user.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        Optional<User> createdUser = userService.createUser(user);
        assertTrue(createdUser.isPresent());
        assertEquals("encodedPassword", createdUser.get().getPassword());
    }

    @Test
    void updateUser() {
        User user = new User();
        user.setId("1");
        user.setUsername("testuser");

        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        Optional<User> updatedUser = userService.updateUser(user);
        assertTrue(updatedUser.isPresent());
        assertEquals("testuser", updatedUser.get().getUsername());
    }

    @Test
    void deleteUserById() {
        when(userRepository.findById("1")).thenReturn(Optional.of(new User()));
        assertTrue(userService.deleteUserById("1"));
        verify(userRepository, times(1)).deleteById("1");
    }

    @Test
    void deleteUsers() {
        userService.deleteUsers();
        verify(userRepository, times(1)).deleteAll();
    }

    @Test
    void hasUsers() {
        when(userRepository.count()).thenReturn(1L);
        assertTrue(userService.hasUsers());
    }

    @Test
    void getUserByUsername() {
        when(userRepository.findUserByUsername("testuser")).thenReturn(new User());
        assertNotNull(userService.getUserByUsername("testuser"));
    }
}
