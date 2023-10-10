package com.ejercicio.tienda.mapper;

import com.ejercicio.tienda.dto.request.CarritoDTO;
import com.ejercicio.tienda.persistence.entity.Carrito;
import com.ejercicio.tienda.persistence.entity.Enum.EstadoCarrito;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class CarritoMap implements IMapper<CarritoDTO, Carrito> {

    @Override
    public Carrito map(CarritoDTO carritoDTO) {
        Carrito carrito = new Carrito();
        carrito.setEstadoCarrito(EstadoCarrito.ACTIVO);
        carrito.setNroCarrito(carritoDTO.getNroCarrito());
        return carrito;
    }

    @Override
    public String mapTest(CarritoDTO carritoDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(carritoDTO);
    }
}
