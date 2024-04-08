package com.webshop.webshopprojektarbete.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/access-denied")
    public String accessDeniedPage() {
        return "access-denied"; // Återvänd vyn du vill visa för åtkomst nekad
    }
}
