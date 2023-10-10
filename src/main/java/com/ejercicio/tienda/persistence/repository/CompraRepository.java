package com.ejercicio.tienda.persistence.repository;

import com.ejercicio.tienda.persistence.entity.Carrito;
import com.ejercicio.tienda.persistence.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompraRepository extends JpaRepository<Compra,Long> {

    @Query(value = "SELECT * FROM compra where carrito_id=:idcarrito", nativeQuery = true)
    public List<Compra> findByAllCarrito(@Param("idcarrito") Long idcarrito);

    @Query(value = "SELECT com.id FROM carrito as car LEFT JOIN compra as com ON car.id = com.carrito_id WHERE car.estado_carrito = 0", nativeQuery = true)
    public List<Long> findByAllCarritoCompra();
}
