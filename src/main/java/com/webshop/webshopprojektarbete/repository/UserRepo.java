package com.webshop.webshopprojektarbete.repository;

import com.webshop.webshopprojektarbete.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Users, String> {
    Optional<Users> findByEmailIgnoreCase(String email);

    boolean existsByEmail(String email);
}
