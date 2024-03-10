package com.webshop.webshopprojektarbete.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendSimpleMailMessage(String to, String token);

}
