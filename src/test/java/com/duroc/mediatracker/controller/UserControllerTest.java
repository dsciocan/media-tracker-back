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


    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void getUser() throws Exception {
        AppUser user = new AppUser(1L, "1244");

        Mockito.when(userService.getUser()).thenReturn(user);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/mediatracker/users/auth"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(status().isOk()
                );
    }


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