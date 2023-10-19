package com.waff.rest.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waff.rest.demo.dto.UserDto;
import com.waff.rest.demo.model.User;
import com.waff.rest.demo.model.UserRole;
import com.waff.rest.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void getUsers() throws Exception {
        when(userService.getUsers()).thenReturn(Collections.singletonList(new User()));
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1));
    }

    @Test
    void createUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("testuser");
        userDto.setPassword("password");

        User createdUser = new User();
        createdUser.setUsername(userDto.getUsername());
        createdUser.setPassword(userDto.getPassword());

        when(userService.createUser(Mockito.any(User.class))).thenReturn(Optional.of(createdUser));

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(userDto.getUsername()));
    }

    @Test
    void getUserById() throws Exception {
        User user = new User();
        user.setId("1");
        when(userService.getUserById("1")).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    void updateUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId("1");
        userDto.setUsername("testuser");

        User updatedUser = new User();
        updatedUser.setId(userDto.getId());
        updatedUser.setUsername(userDto.getUsername());

        when(userService.updateUser(Mockito.any(User.class))).thenReturn(Optional.of(updatedUser));

        mockMvc.perform(MockMvcRequestBuilders.put("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(userDto.getUsername()));
    }

    @Test
    void deleteUser() throws Exception {
        when(userService.deleteUserById("1")).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/user/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findUsersByType() throws Exception {
        when(userService.getUserByUserType(UserRole.USER)).thenReturn(Collections.singletonList(new User()));

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/user/user_type/USER"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1));
    }

    @Test
    void isAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/user/role"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
