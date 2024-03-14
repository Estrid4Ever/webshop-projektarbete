package com.webshop.webshopprojektarbete.repository;

import com.webshop.webshopprojektarbete.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Products, Integer> {
    List<Products> findByName(String name);
    List<Products> findByBrand(String name);
    List<Products> findByColor(String name);
}
