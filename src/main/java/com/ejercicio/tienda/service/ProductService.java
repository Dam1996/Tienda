package com.ejercicio.tienda.service;

import com.ejercicio.tienda.dto.request.ProductRequest;
import com.ejercicio.tienda.exceptions.Exceptions;
import com.ejercicio.tienda.mapper.ProductMap;
import com.ejercicio.tienda.persistence.entity.Product;
import com.ejercicio.tienda.persistence.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMap productMap;
    public Product createProduct(ProductRequest productRequest){
        Product product = productMap.map(productRequest);
        return productRepository.save(product);
    }
    public Product getById(Long id){
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) throw new Exceptions("No existe el producto", HttpStatus.NOT_FOUND);
        return product.get();
    }
    public Product updateProduct(Long id, ProductRequest productRequest){
        Product product = getById(id);
        product.setPrecio(productRequest.getPrecio());
        product.setDescripcion(productRequest.getDescripcion());
        product.setNombre(productRequest.getNombre());
        product.setStock(productRequest.getStock());
        product.setCantidad(productRequest.getCantidad());
        product.setEstado(productRequest.getEstado());
        return this.productRepository.save(product);
    }

    public Product updateProductSinDto(Long id, Product product){
        return this.productRepository.save(product);
    }

    public void delete(Long id){
        getById(id);
        this.productRepository.deleteById(id);
    }

    public List<Product> getAll(){
        return productRepository.findAll();
    }
}
