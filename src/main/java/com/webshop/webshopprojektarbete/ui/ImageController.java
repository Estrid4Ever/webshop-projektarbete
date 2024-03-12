package com.webshop.webshopprojektarbete.ui;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class ImageController {

    @GetMapping("/image") //url example to get here: localhost:8080/image?path=/img/sunglasses.jpg
    public ResponseEntity<byte[]> getImage(@RequestParam String path) throws IOException {
        // Load the image file
        Resource resource = new ClassPathResource(path);
        byte[] imageBytes = StreamUtils.copyToByteArray(resource.getInputStream());

        // Set response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(imageBytes.length);
        //headers.setCacheControl("no-store"); // Prevent caching

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
}

