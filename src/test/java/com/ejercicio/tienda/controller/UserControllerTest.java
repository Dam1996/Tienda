package com.ejercicio.tienda.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@WebAppConfiguration
class UserControllerTest {
    private final static String URL = "/users";
    MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getAll() throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("Midas","damian");
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(URL)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void getAll_no_auth_fail() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(URL)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andReturn();
    }
}