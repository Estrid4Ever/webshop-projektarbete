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

import java.util.Hashtable;
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
        Hashtable<Products, Integer> shoppingCart = shoppingCartService.getShoppingCart();
        String cartTotal = shoppingCartService.getShoppingCartTotal();
        model.addAttribute("total", cartTotal);
        model.addAttribute("shoppingcart", shoppingCart);
        model.addAttribute("allproducts", allProducts);
        return "index";
    }

    @GetMapping("/searchpage")
    public String doSearch() {
        return "searchpage";
    }

    @PostMapping("/sort")
    public String sortProducts(@RequestParam String sort, Model model) {
        List<Products> allProducts = productService.fetchAllProducts();
        Hashtable<Products, Integer> shoppingCart = shoppingCartService.getShoppingCart();
        String cartTotal = shoppingCartService.getShoppingCartTotal();
        model.addAttribute("total", cartTotal);
        model.addAttribute("shoppingcart", shoppingCart);
        model.addAttribute("allproducts", productService.sortProducts(allProducts, sort));
        return "index";
    }


    @PostMapping("/search")
    public String searchProduct(@RequestParam String searchItem, Model model) {
        List<Products> p = productService.findings(searchItem);

        model.addAttribute("searchItem", productService.sortProducts(p, "alphabetical"));
        return "searchpage";
    }
    @GetMapping("/clear-cart")
    public String clearCart(Model model) {
        shoppingCartService.clearShoppingCart();
        List<Products> allProducts = productService.fetchAllProducts();
        model.addAttribute("allproducts", allProducts);
        return "index";
    }

    @GetMapping("/add-to-cart")
    public String addItemToCart(@RequestParam("item-id") String itemId, Model model) {
        shoppingCartService.addProductToCart(Integer.parseInt(itemId));
        return "redirect:/";
    }

    @GetMapping("/remove-from-cart")
    public String removeItemFromCart(@RequestParam("item-id") String itemId,
                                     @RequestParam("amount") String amount, Model model) {

        int removeAmount = Integer.parseInt(amount);

        for (int i = 0; i < removeAmount; i++) {
            shoppingCartService.removeProductFromCart(Integer.parseInt(itemId));
        }
        return "redirect:/";
    }
}
