package com.ejercicio.tienda.persistence.entity;

import com.ejercicio.tienda.persistence.entity.Enum.EstadoCarrito;
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
public class Carrito {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "nro_carrito")
    private Long nroCarrito;
    @Column(name = "estado_carrito")
    private EstadoCarrito estadoCarrito;
}
