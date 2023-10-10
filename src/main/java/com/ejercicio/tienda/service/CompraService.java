package com.ejercicio.tienda.service;

import com.ejercicio.tienda.dto.request.CarritoDTO;
import com.ejercicio.tienda.persistence.entity.Carrito;
import com.ejercicio.tienda.persistence.entity.Compra;
import com.ejercicio.tienda.persistence.entity.Product;
import com.ejercicio.tienda.persistence.repository.CompraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompraService {
    private final CompraRepository compraRepository;

    public Compra createCompra(Carrito carrito, Product product, CarritoDTO carritoDTO){
        Compra compra = new Compra();
        compra.setCarrito(carrito);
        compra.setProduct(product);
        compra.setCantidad(carritoDTO.getCantidad());
        return compraRepository.save(compra);
    }

    public Compra getById(Long id){
        Optional<Compra> optionalCompra = compraRepository.findById(id);
        return optionalCompra.get();
    }

    public List<Compra> findAllByCarrito(Long id){
        return compraRepository.findByAllCarrito(id);
    }
    public List<Long> findByAllCarritoCompra(){
        return compraRepository.findByAllCarritoCompra();
    }
}
