package com.ejercicio.tienda.controller;

import com.ejercicio.tienda.dto.request.CarritoDTO;
import com.ejercicio.tienda.dto.request.ProductRequest;
import com.ejercicio.tienda.exceptions.Exceptions;
import com.ejercicio.tienda.persistence.entity.Carrito;
import com.ejercicio.tienda.persistence.entity.Compra;
import com.ejercicio.tienda.persistence.entity.Enum.EstadoCarrito;
import com.ejercicio.tienda.persistence.entity.Product;
import com.ejercicio.tienda.service.AuthService;
import com.ejercicio.tienda.service.CarritoService;
import com.ejercicio.tienda.service.CompraService;
import com.ejercicio.tienda.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carritos")
public class CarritoController {
    private final CarritoService carritoService;
    private final ProductService productService;
    @Autowired
    private final CompraService compraService;

    @PostMapping
    @PreAuthorize("hasAuthority('CLIENTE')")
    @SecurityRequirement(name = "Bearer Authentication")
    public Compra createCompra(@RequestBody CarritoDTO carritoDTO){
        Carrito carrito;
        Product product = productService.getById(carritoDTO.getIdProducto());
        if (carritoDTO.getCantidad() > product.getStock()) throw new Exceptions("Supero el stock. El stock disponible es de " + product.getStock(), HttpStatus.BAD_REQUEST);
        if (carritoDTO.getNroCarrito() == null){
            carrito = null;
        } else {
            carrito = carritoService.existeCarritoValido(carritoDTO.getNroCarrito());
        }

        if (carrito == null) carrito = this.carritoService.crearCarrito(carritoDTO);
        return this.compraService.createCompra(carrito,product,carritoDTO);
    }

    @PutMapping("/confirmarCompra/{nroCarrito}")
    @PreAuthorize("hasAuthority('CLIENTE')")
    @SecurityRequirement(name = "Bearer Authentication")
    public Carrito efectuarCompra(@PathVariable("nroCarrito") Long id){
        Carrito carrito = carritoService.getById(id);
        List<Compra> compraList = compraService.findAllByCarrito(id);
        long stock;
        for (Compra compra : compraList
             ) {
            stock = compra.getProduct().getStock() - compra.getCantidad();
            compra.getProduct().setStock(stock);
            this.productService.updateProductSinDto(compra.getProduct().getId(),compra.getProduct());
        }
        carrito.setEstadoCarrito(EstadoCarrito.FINALIZADO);
        return this.carritoService.update(carrito);
    }

    @GetMapping("/comprasRealizadas")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<Compra> findAllByNroCarrito(){
        List<Long> idCompras =  compraService.findByAllCarritoCompra();
        List<Compra> compraList = new ArrayList<>();

        for (Long idCompra: idCompras
             ) {
            Compra compra = compraService.getById(idCompra);
            compraList.add(compra);
        }
        return compraList;
    }
}
