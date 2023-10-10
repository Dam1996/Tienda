package com.ejercicio.tienda.persistence.repository;

import com.ejercicio.tienda.persistence.entity.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarritoRepository extends JpaRepository<Carrito,Long> {
}
