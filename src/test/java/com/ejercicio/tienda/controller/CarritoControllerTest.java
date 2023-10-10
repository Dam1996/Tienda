package com.ejercicio.tienda.controller;

import com.ejercicio.tienda.dto.request.CarritoDTO;
import com.ejercicio.tienda.dto.request.ProductRequest;
import com.ejercicio.tienda.dto.request.UserDTO;
import com.ejercicio.tienda.mapper.CarritoMap;
import com.ejercicio.tienda.mapper.ProductMap;
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

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@WebAppConfiguration
class CarritoControllerTest {
    private final static String URL = "/carritos";
    MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ProductMap productMap;
    @Autowired
    private UserMap userMap;
    @Autowired
    private CarritoMap carritoMap;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void createCompra() throws Exception {
        createClient();
        createProduct();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("test-user","damian");
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .content(carritoMap.mapTest(createCarritoDTO()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }
    @Test
    void generar_error_no_existe_producto() throws Exception {
        createClient();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("test-user","damian");
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .content(carritoMap.mapTest(createCarritoDTO()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(404,mvcResult.getResponse().getStatus());
    }

    @Test
    void generar_error_stock() throws Exception {
        createClient();
        createProduct();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("test-user","damian");
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .content(carritoMap.mapTest(createOtroCarritoDTO()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(400,mvcResult.getResponse().getStatus());
    }

    private void createProduct() throws Exception{
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("Midas","damian");
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        BigDecimal bigDecimal = new BigDecimal(1000);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setCantidad(100);
        productRequest.setEstado("");
        productRequest.setStock(100);
        productRequest.setPrecio(bigDecimal);
        productRequest.setDescripcion("");
        productRequest.setNombre("Remera");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .content(productMap.mapTest(productRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
    }
    private CarritoDTO createOtroCarritoDTO(){
        BigDecimal bigDecimal = new BigDecimal(1000);
        CarritoDTO carritoDTO = new CarritoDTO();
        long cantidad = 200;
        long nroCarrito = 10;
        long idProducto = 1;
        carritoDTO.setCantidad(cantidad);
        carritoDTO.setNroCarrito(nroCarrito);
        carritoDTO.setIdProducto(idProducto);
        return carritoDTO;
    }
    private CarritoDTO createCarritoDTO(){
        BigDecimal bigDecimal = new BigDecimal(1000);
        CarritoDTO carritoDTO = new CarritoDTO();
        long cantidad = 20;
        long nroCarrito = 10;
        long idProducto = 1;
        carritoDTO.setCantidad(cantidad);
        carritoDTO.setNroCarrito(nroCarrito);
        carritoDTO.setIdProducto(idProducto);
        return carritoDTO;
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