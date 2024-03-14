package com.webshop.webshopprojektarbete.service;

import com.webshop.webshopprojektarbete.entity.Products;
import com.webshop.webshopprojektarbete.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
