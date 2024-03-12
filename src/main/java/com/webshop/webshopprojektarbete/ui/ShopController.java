package com.webshop.webshopprojektarbete.ui;

import com.webshop.webshopprojektarbete.entity.Products;
import com.webshop.webshopprojektarbete.service.ProductService;
import com.webshop.webshopprojektarbete.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ShopController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/")
    public String doGet(Model model) {
        List<Products> allProducts = productService.fetchAllProducts();
        model.addAttribute("allproducts", allProducts);
        return "index";
    }

    @PostMapping("/sort")
    public String sortProducts(@RequestParam String sort, Model model) {
        List<Products> allProducts = productService.fetchAllProducts();
        productService.sortProducts(allProducts, sort);
        model.addAttribute("allproducts", allProducts);
        return "index";
    }

}
