package com.ejercicio.tienda.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Long cantidad;
    private String estado;
    private Long stock;
}
