package com.webshop.webshopprojektarbete.service;

import com.webshop.webshopprojektarbete.entity.Products;
import com.webshop.webshopprojektarbete.repository.ProductRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;

@Service
@SessionScope
public class ShoppingCartService {
    private Hashtable<Products, Integer> shoppingCart;

    @Autowired
    private ProductRepo productRepo;

    @PostConstruct
    public void init() {
        shoppingCart = new Hashtable<Products, Integer>();
    }

    public void addProductToCart(int id) {
        Optional<Products> optionalProduct = productRepo.findById(id);
        Products product;
        if (optionalProduct.isEmpty()) {
            return;
        } else {
            product = optionalProduct.get();
        }
        if (shoppingCart.containsKey(product)) {
            int currentValue = shoppingCart.get(product);
            int updatedValue = currentValue + 1;
            shoppingCart.put(product, updatedValue);
        } else {
            shoppingCart.put(product, 1);
        }
    }

    public void removeProductFromCart(int id) {
        Optional<Products> optionalProduct = productRepo.findById(id);
        Products product;
        if (optionalProduct.isEmpty()) {
            return;
        } else {
            product = optionalProduct.get();
        }

        if (shoppingCart.containsKey(product) && shoppingCart.get(product) > 1) {
            int currentValue = shoppingCart.get(product);
            int updatedValue = currentValue - 1;
            shoppingCart.put(product, updatedValue);
        } else if(shoppingCart.containsKey(product)) {
            shoppingCart.remove(product);
        }
    }

    public void clearShoppingCart() {
        shoppingCart.clear();
    }

    public Hashtable<Products, Integer> getShoppingCart() {
        return shoppingCart;
    }
    public int getShoppingCartTotal() {
        int total = 0;

        for (Map.Entry<Products, Integer> entry : shoppingCart.entrySet()) {
            total += (entry.getKey().getPrice() * entry.getValue());
        }
        return total;
    }
}
