package com.webshop.webshopprojektarbete.ui;

import com.webshop.webshopprojektarbete.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ImageController {

    @Autowired
    ProductService productService;

    @GetMapping("/image") //url example to get here: localhost:8080/image?path=/img/sunglasses.jpg
    public ResponseEntity<byte[]> getImage(@RequestParam String path) {
        byte[] imageBytes = productService.getImageBytes(path);

        HttpHeaders headers = productService.getImageHeaders(imageBytes.length);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
}

