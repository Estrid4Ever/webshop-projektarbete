package com.webshop.webshopprojektarbete.repository;

import com.webshop.webshopprojektarbete.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {
    Order findById(int id);
    List<Order> findAllByStatus (Object status);
}
