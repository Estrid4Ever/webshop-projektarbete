package com.webshop.webshopprojektarbete.repository;

import com.webshop.webshopprojektarbete.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, String> {
    Users findByEmailIgnoreCase(String email);

    boolean existsByEmail(String email);
}
