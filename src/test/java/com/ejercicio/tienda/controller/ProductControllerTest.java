package com.ejercicio.tienda.controller;

import com.ejercicio.tienda.dto.request.ProductRequest;
import com.ejercicio.tienda.dto.request.UserDTO;
import com.ejercicio.tienda.mapper.ProductMap;
import com.ejercicio.tienda.mapper.UserMap;
import com.ejercicio.tienda.persistence.entity.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@WebAppConfiguration
class ProductControllerTest {
    private final static String URL = "/products";
    MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ProductMap productMap;
    @Autowired
    private UserMap userMap;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void create() throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("Midas","damian");
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        ProductRequest productRequest = createProduct();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .content(productMap.mapTest(productRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void update() throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("Midas","damian");
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        ProductRequest productRequest = createProduct();
        MvcResult mvcResultPost = post(productRequest);
        productRequest.setCantidad(500);
        MvcResult mvcResultPut = mockMvc.perform(MockMvcRequestBuilders.put(URL + "/1")
                .content(productMap.mapTest(productRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(200,mvcResultPut.getResponse().getStatus());
    }

    private MvcResult post(ProductRequest productRequest) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .content(productMap.mapTest(productRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
    }

    @Test
    void update_not_found_404() throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("Midas","damian");
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        ProductRequest productRequest = createProduct();
        MvcResult mvcResultPost = post(productRequest);
        productRequest.setCantidad(500);
        MvcResult mvcResultPut = mockMvc.perform(MockMvcRequestBuilders.put(URL + "/22")
                        .content(productMap.mapTest(productRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(404,mvcResultPut.getResponse().getStatus());
    }

    @Test
    void delete() throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("Midas","damian");
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        ProductRequest productRequest = createProduct();
        MvcResult mvcResultPost = post(productRequest);
        MvcResult mvcResultPut = mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(204,mvcResultPut.getResponse().getStatus());
    }

    @Test
    void generar_error_traer_productos_solo_clientes() throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("Midas","damian");
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(URL)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void getAll_success() throws Exception {
        createClient();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("test-user","damian");
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(URL)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }
    private ProductRequest createProduct(){
        BigDecimal bigDecimal = new BigDecimal(1000);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setCantidad(100);
        productRequest.setEstado("");
        productRequest.setStock(1000);
        productRequest.setPrecio(bigDecimal);
        productRequest.setDescripcion("");
        productRequest.setNombre("Remera");
        return productRequest;
    }
    private void createClient() throws Exception{
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("test-user");
        userDTO.setPassword("damian");
        MvcResult mvcResultUser = mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .content(userMap.mapTest(userDTO))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
    }
}