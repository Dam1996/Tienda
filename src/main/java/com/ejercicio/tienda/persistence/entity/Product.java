package com.ejercicio.tienda.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private BigDecimal precio;
    @Column(name = "cantidad_product")
    private Long cantidad;
    private String descripcion;
    private String estado;
    private Long stock;
}
