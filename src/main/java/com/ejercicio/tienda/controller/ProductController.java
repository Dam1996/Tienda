package com.ejercicio.tienda.controller;

import com.ejercicio.tienda.dto.request.ProductRequest;
import com.ejercicio.tienda.persistence.entity.Product;
import com.ejercicio.tienda.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @SecurityRequirement(name = "Bearer Authentication")
    public Product createProduct(@RequestBody ProductRequest productRequest){
        return this.productService.createProduct(productRequest);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @SecurityRequirement(name = "Bearer Authentication")
    public Product updateProduct(@PathVariable("id") Long id,@RequestBody ProductRequest productRequest){
        return this.productService.updateProduct(id,productRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        this.productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    @PreAuthorize("hasAuthority('CLIENTE')")
    public List<Product> getAll(){
        return productService.getAll();
    }
}
