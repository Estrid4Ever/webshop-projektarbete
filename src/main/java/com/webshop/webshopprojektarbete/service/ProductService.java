package com.webshop.webshopprojektarbete.service;

import com.webshop.webshopprojektarbete.entity.Products;
import com.webshop.webshopprojektarbete.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public List<Products> fetchAllProducts() {
        return productRepo.findAll();
    }
    public List<Products> findByName(String s){
        return productRepo.findByName(s);
    }
    public List<Products> findByBrand(String s){
        return productRepo.findByBrand(s);
    }
    public List<Products> findByColor(String s){
        return productRepo.findByColor(s);
    }
    public List<Products> findings(String s){
        List<Products> found = new ArrayList<>();
        found.addAll(findByBrand(s));
        found.addAll(findByColor(s));
        found.addAll(findByName(s));

        return found;
    }

    public List<Products> sortProducts(List<Products> allProducts, String sortType) {
        if (sortType.equalsIgnoreCase("price high")) {
            allProducts.sort(Comparator.comparingInt(Products::getPrice).reversed());
        } else if (sortType.equalsIgnoreCase("alphabetical")) {
            allProducts.sort(Comparator.comparing(Products::getName));
        } else if (sortType.equalsIgnoreCase("Popularity")) {
            Collections.shuffle(allProducts);
        } else {
            allProducts.sort(Comparator.comparingInt(Products::getPrice));
        }
        return allProducts;
    }
    public void addNewProduct(Products p){
        productRepo.save(p);

    }
    public String convertImagePath(MultipartFile file){
        return "/image?path=/img/" + file.getOriginalFilename();
    }


    public String handleFileUploadTARGET(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                // Hämta sökvägen till mappen "img" under "resources"
                String uploadDir = new ClassPathResource("img").getFile().getAbsolutePath();

                // Skapa filvägen för den sparade filen
                String filePath = uploadDir + File.separator + file.getOriginalFilename();

                // Skapa en ny File-instans för den sparade filen
                File dest = new File(filePath);

                // Överför innehållet i den uppladdade filen till destinationsfilen
                file.transferTo(dest);

                // Returnera den sparade filens filväg
                return filePath;
            } catch (IOException e) {
                e.printStackTrace();
                return "Failed to upload file";
            }
        } else {
            return "File is empty";
        }
    }
    public HttpHeaders getImageHeaders(int imageBytesLength) {
        // Set response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(imageBytesLength);
        //headers.setCacheControl("no-store"); // Prevent caching

        return headers;
    }

    public byte[] getImageBytes(String path) {
        // Load the image file
        Resource resource = new ClassPathResource(path);
        byte[] imageBytes;
        try {
            imageBytes = StreamUtils.copyToByteArray(resource.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return imageBytes;
    }
}
