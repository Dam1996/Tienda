package com.ejercicio.tienda.dto.request;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class CarritoDTO {
    private Long idProducto;
    private Long nroCarrito;
    private Long cantidad;
}
