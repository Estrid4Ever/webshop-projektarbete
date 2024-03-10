package com.webshop.webshopprojektarbete.repository;

import com.webshop.webshopprojektarbete.entity.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationRepo extends JpaRepository<Confirmation, Integer> {
    Confirmation findByUserId(String useroEmail);

    Optional<Confirmation> findByToken(String token);
}
