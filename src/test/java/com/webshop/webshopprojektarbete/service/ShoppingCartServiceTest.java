package com.webshop.webshopprojektarbete.service;

import com.webshop.webshopprojektarbete.entity.Products;
import com.webshop.webshopprojektarbete.repository.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceTest {

    @InjectMocks
    ShoppingCartService shoppingCartService;

    @Mock
    ProductRepo productRepo;

    @BeforeEach
    void setUp() {
        shoppingCartService.init();
        shoppingCartService.setProductRepo(productRepo);
        Products p = new Products(
                "name", "color", 1,
                "brand", 199, "/imagepath");

        when(productRepo.findById(1)).thenReturn(Optional.of(p));
    }

    @Test
    void addProductToCart() {
        shoppingCartService.addProductToCart(1);
        assertEquals(1, shoppingCartService.getShoppingCart().size());
    }

    @Test
    void clearShoppingCartTest() {
        shoppingCartService.addProductToCart(1);
        shoppingCartService.clearShoppingCart();
        assertTrue(shoppingCartService.getShoppingCart().isEmpty());
    }

    @Test
    void getShoppingCartTotal() {
        shoppingCartService.addProductToCart(1);
        assertEquals("Total: 199 sek", shoppingCartService.getShoppingCartTotal());
    }
}