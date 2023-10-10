package com.ejercicio.tienda.service;

import com.ejercicio.tienda.dto.request.CarritoDTO;
import com.ejercicio.tienda.exceptions.Exceptions;
import com.ejercicio.tienda.mapper.CarritoMap;
import com.ejercicio.tienda.persistence.entity.*;
import com.ejercicio.tienda.persistence.entity.Enum.EstadoCarrito;
import com.ejercicio.tienda.persistence.repository.CarritoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarritoService {
    private final CarritoRepository carritoRepository;
    private final CarritoMap carritoMap;
    public Carrito getById(Long id){
        Optional<Carrito> optionalCarrito = carritoRepository.findById(id);
        if (optionalCarrito.isEmpty()) throw new Exceptions("No existe el carrito", HttpStatus.NOT_FOUND);
        return optionalCarrito.get();
    }
    public Carrito existeCarritoValido(Long id){
        Optional<Carrito> optionalCarrito = carritoRepository.findById(id);
        if (optionalCarrito.isEmpty()) return null;
        Carrito carrito = optionalCarrito.get();
        if (carrito.equals(EstadoCarrito.FINALIZADO)) throw new Exceptions("Ingresaste el nro de carrito " + carrito.getNroCarrito() + " pero ya esta efectuada la compra.", HttpStatus.BAD_REQUEST);
            return carrito;
    }

    public Carrito update(Carrito carrito){
        return this.carritoRepository.save(carrito);
    }

    public Carrito crearCarrito(CarritoDTO carritoDTO){
        Carrito carrito = carritoMap.map(carritoDTO);

        Carrito carritoSave = carritoRepository.save(carrito);
        carritoSave.setNroCarrito(carritoSave.getId());
        return carritoRepository.save(carritoSave);
    }
}
