package com.webshop.webshopprojektarbete.service;

import com.webshop.webshopprojektarbete.entity.Products;
import org.springframework.stereotype.Service;

import java.util.Hashtable;

@Service
public interface EmailService {
    void sendVerificationToken(String to, String token);

    void sendOrderVerification(String to, Hashtable<Products, Integer> items);
}
