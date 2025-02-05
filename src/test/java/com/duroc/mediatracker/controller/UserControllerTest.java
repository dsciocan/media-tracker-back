package com.duroc.mediatracker.controller;

import com.duroc.mediatracker.model.user.AppUser;
import com.duroc.mediatracker.service.ShowService;
import com.duroc.mediatracker.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    void getUser() throws Exception {
//        FirebaseToken token = (FirebaseToken) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String uid = token.getUid();
        AppUser user = new AppUser(1L, "1244");

        Mockito.when(userService.getUser()).thenReturn(user);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/mediatracker/users/auth"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.oAuthId").value(34459880L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(status().isOk()
                );
    }

//    @Test
//    void changeUsername() throws Exception {
//        Long userId = 1L;
//        String username = "username";
//        Long oAuthId = 1L;
//        AppUser user = AppUser.builder().id(userId).username(username).oAuthId(oAuthId).build();
//
//        when(userService.changeUsername(userId, username)).thenReturn(user);
//
//        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/mediatracker/users/{userId}", userId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(username))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(userId))
//                .andExpect(jsonPath("$.username").value(username));
//
//        verify(userService).changeUsername(userId, username);
//    }

    @Test
    void deleteUser() throws Exception {
        Long userId = 1L;
        String message = "User deleted successfully";

        when(userService.deleteUser()).thenReturn(message);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/mediatracker/users/delete", userId))
                .andExpect(status().isOk())
                .andExpect(content().string(message));

        verify(userService).deleteUser();
    }
}