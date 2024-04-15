package com.webshop.webshopprojektarbete.service;

import com.webshop.webshopprojektarbete.entity.Products;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Hashtable;

@Service
@SessionScope
public class CheckOutService {


    private Hashtable<Products, Integer> shoppingCart;

    public CheckOutService(@Lazy ShoppingCartService shoppingCartService) {
        this.shoppingCart = shoppingCartService.getShoppingCart();
    }


    public Hashtable<Products, Integer> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(Hashtable<Products, Integer> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}

