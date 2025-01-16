package com.duroc.mediatracker.controller;

import com.duroc.mediatracker.model.user.AppUser;
import com.duroc.mediatracker.service.ShowService;
import com.duroc.mediatracker.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        mapper = new ObjectMapper();
    }

    @Test
    void getUserById() throws Exception {
        AppUser user = new AppUser(1L, 34459880L, "someone");

        Mockito.when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/mediatracker/users/1"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.oAuthId").value(34459880L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("someone"))
                .andExpect(status().isOk()
                );
    }

    @Test
    void saveUser() throws Exception {
        AppUser user = new AppUser(1L, 34459880L, "someone");
        String json = mapper.writeValueAsString(user);
        Mockito.when(userService.saveUser(user)).thenReturn(user);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/mediatracker/users/save").contentType(MediaType.APPLICATION_JSON).content(json))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.oAuthId").value(34459880L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("someone"))
                .andExpect(status().isOk()
        );
    }

    @Test
    void changeUsername() {
    }

    @Test
    void deleteUser() {
    }
}