package com.waff.rest.demo.controller;

import com.waff.rest.demo.config.CustomUserDetails;
import com.waff.rest.demo.dto.UserDto;
import com.waff.rest.demo.model.User;
import com.waff.rest.demo.model.UserRole;
import com.waff.rest.demo.security.JwtTokenFilter;
import com.waff.rest.demo.security.JwtUtils;
import com.waff.rest.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ModelMapper modelMapper;

    @Mock
    private JwtTokenFilter jwtTokenFilter;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private org.springframework.security.core.userdetails.UserDetailsService userDetailsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        modelMapper = new ModelMapper();
        userController = new UserController(userService, modelMapper);

        UserDetails userDetails = new CustomUserDetails("admin", "password", new ArrayList<>());

        when(userDetailsService.loadUserByUsername("admin")).thenReturn(userDetails);

        User admin = new User();
        admin.setId("1");
        admin.setUsername("admin");
        admin.setEmail("admin@test.com");
        admin.setFirstname("Admin");
        admin.setLastname("Admin");
        admin.setUserType(UserRole.ADMIN);
        admin.setPassword("admin");
        admin.setEnabled(true);

        when(userService.getUserById("1")).thenReturn(Optional.of(admin));

        when(jwtUtils.validateJwtToken("valid_token")).thenReturn(true);
        when(jwtUtils.validateJwtToken("invalid_token")).thenReturn(false);
    }

    @Test
    public void testGetUsers() {
        UserDetails userDetails = new CustomUserDetails("admin", "password", new ArrayList<>());
        jwtTokenFilter.setAuthentication(userDetails);

        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        when(userService.getUsers()).thenReturn(users);

        ResponseEntity<List<User>> responseEntity = userController.getUsers();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(users, responseEntity.getBody());
    }

    // @Test
    // public void testCreateUser() {
    //     UserDetails userDetails = new CustomUserDetails("admin", "password", new ArrayList<>());
    //     jwtTokenFilter.setAuthentication(userDetails);

    //     UserDto userDto = new UserDto();
    //     userDto.setFirstname("John");
    //     userDto.setLastname("Doe");
    //     userDto.setUsername("johndoe");
    //     userDto.setEmail("john@example.com");
    //     userDto.setUserType(UserRole.USER);
    //     userDto.setPassword("password");

    //     User user = modelMapper.map(userDto, User.class);

    //     when(userService.createUser(user)).thenReturn(Optional.of(user));

    //     ResponseEntity<User> responseEntity = userController.createUser(userDto);

    //     assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    //     assertEquals(user, responseEntity.getBody());
    // }

    @Test
    public void testGetUserById() {
        UserDetails userDetails = new CustomUserDetails("admin", "password", new ArrayList<>());
        jwtTokenFilter.setAuthentication(userDetails);

        String userId = "1";
        User admin = new User();
        admin.setId(userId);
        admin.setUsername("admin");

        when(userService.getUserById(userId)).thenReturn(Optional.of(admin));

        ResponseEntity<?> responseEntity = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(admin, responseEntity.getBody());
    }

    // @Test
    // public void testUpdateUser() {
    //     UserDetails userDetails = new CustomUserDetails("admin", "password", new ArrayList<>());
    //     jwtTokenFilter.setAuthentication(userDetails);

    //     UserDto userDto = new UserDto();
    //     userDto.setFirstname("Updated");
    //     userDto.setLastname("User");
    //     userDto.setUsername("updateduser");
    //     userDto.setEmail("updated@example.com");
    //     userDto.setUserType(UserRole.ADMIN);
    //     userDto.setPassword("newpassword");

    //     User user = modelMapper.map(userDto, User.class);

    //     when(userService.updateUser(user)).thenReturn(Optional.of(user));

    //     ResponseEntity<User> responseEntity = userController.updateUser(userDto);

    //     assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    //     assertEquals(user, responseEntity.getBody());
    // }

    // @Test
    // public void testDeleteUser() {
    //     UserDetails userDetails = new CustomUserDetails("admin", "password", new ArrayList<>());
    //     jwtTokenFilter.setAuthentication(userDetails);

    //     String userId = "1";
    //     when(userService.deleteUserById(userId)).thenReturn(true);

    //     ResponseEntity<Void> responseEntity = userController.deleteUser(userId);

    //     assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    // }

    @Test
    public void testFindUsersByType() {
        UserDetails userDetails = new CustomUserDetails("admin", "password", new ArrayList<>());
        jwtTokenFilter.setAuthentication(userDetails);

        UserRole userType = UserRole.ADMIN;
        List<User> users = new ArrayList<>();
        users.add(new User());

        when(userService.getUserByUserType(userType)).thenReturn(users);

        ResponseEntity<List<User>> responseEntity = userController.findUsersByType(userType);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(users, responseEntity.getBody());
    }
}
