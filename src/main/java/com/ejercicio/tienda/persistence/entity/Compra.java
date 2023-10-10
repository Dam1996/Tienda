package com.ejercicio.tienda.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Compra {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "producto_id",nullable = false)
    private Product product;
    @ManyToOne
    @JoinColumn(name = "carrito_id",nullable = false)
    private Carrito carrito;
    private Long cantidad;
}
