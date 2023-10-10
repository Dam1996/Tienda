package com.ejercicio.tienda.mapper;

import com.ejercicio.tienda.dto.request.ProductRequest;
import com.ejercicio.tienda.persistence.entity.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMap implements IMapper<ProductRequest, Product> {
    @Override
    public Product map(ProductRequest productRequest) {
        Product product = new Product();
        product.setCantidad(productRequest.getCantidad());
        product.setEstado(productRequest.getEstado());
        product.setPrecio(productRequest.getPrecio());
        product.setDescripcion(productRequest.getDescripcion());
        product.setNombre(productRequest.getNombre());
        product.setStock(productRequest.getStock());
        return product;
    }

    @Override
    public String mapTest(ProductRequest productRequest) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(productRequest);
    }
}
