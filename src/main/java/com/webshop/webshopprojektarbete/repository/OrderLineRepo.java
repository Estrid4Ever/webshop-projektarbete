package com.webshop.webshopprojektarbete.repository;

import com.webshop.webshopprojektarbete.entity.Orderline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineRepo extends JpaRepository<Orderline, Integer> {
}
