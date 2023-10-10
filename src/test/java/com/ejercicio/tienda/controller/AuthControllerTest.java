package com.ejercicio.tienda.controller;

import com.ejercicio.tienda.dto.request.UserDTO;
import com.ejercicio.tienda.mapper.UserMap;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@WebAppConfiguration
class AuthControllerTest {
    private final static String URL = "/auth/";
    MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private UserMap userMap;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void login_success() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("Midas");
        userDTO.setPassword("damian");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(URL + "login")
                        .content(userMap.mapTest(userDTO))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void login_fail_credentials() throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("Midas","21121");
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("fail");
        userDTO.setPassword("credentials");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(URL + "login")
                        .content(userMap.mapTest(userDTO))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
    }

    @Test
    void register() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("test");
        userDTO.setPassword("damian");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(URL + "register")
                        .content(userMap.mapTest(userDTO))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void generar_error_usuario_existente_register() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("Midas");
        userDTO.setPassword("323232");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(URL + "register")
                        .content(userMap.mapTest(userDTO))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(400,mvcResult.getResponse().getStatus());
    }
}