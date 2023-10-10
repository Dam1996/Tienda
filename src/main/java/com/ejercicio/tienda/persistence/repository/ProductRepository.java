package com.ejercicio.tienda.persistence.repository;

import com.ejercicio.tienda.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
