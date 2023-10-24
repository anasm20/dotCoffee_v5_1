package com.waff.rest.demo.IntegrationsTest;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import com.waff.rest.demo.dto.*;
import com.waff.rest.demo.repository.*;
import com.waff.rest.demo.service.UserService;

import io.jsonwebtoken.lang.Arrays;

import com.waff.rest.demo.controller.*;
import com.waff.rest.demo.model.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureDataJpa
@ExtendWith(SpringExtension.class)
@ComponentScan("com.waff.rest.demo")
public class CategoryControllerIntegrationTest{

@Autowired
private MockMvc mockMvc;

@Autowired
private UserController userController;

@Autowired
private UserRepository userRepository;

@Autowired
private UserService userService;

@BeforeEach
    public void setup() {
    }

    @Test
    public void testGetCategory() throws Exception {
        this.mockMvc.perform(get("/category"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    
    // TODO
    //  @Test
    // public void testGetCategoryById() throws Exception {
    //     this.mockMvc.perform(get("/category/{id}"))
    //             .andDo(print())
    //             .andExpect(status().isOk());
    // }

    // ERROR 403
    // @Test
    // public void testGetCart() throws Exception {

    //     this.mockMvc.perform(get("/cart"))
    //             .andDo(print())
    //             .andExpect(status().isOk());
    // }

}