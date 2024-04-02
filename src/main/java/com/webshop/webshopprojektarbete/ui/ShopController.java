package com.webshop.webshopprojektarbete.ui;

import com.webshop.webshopprojektarbete.entity.Products;
import com.webshop.webshopprojektarbete.service.ProductService;
import com.webshop.webshopprojektarbete.service.ShoppingCartService;
import com.webshop.webshopprojektarbete.service.UsersServiceImpl;
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

    @Autowired
    private UsersServiceImpl usersService;

    @GetMapping("/")
    public String doGet(Model model) {
        if (usersService.getUsers().getEnabled() == 0) {
            model.addAttribute("codestatus", "Your email is not verified, please enter your verification code.");
            model.addAttribute("userinfo", usersService.getUsers().getEmail());
            return "verifypage";
        }
        List<Products> allProducts = productService.fetchAllProducts();
        Hashtable<Products, Integer> shoppingCart = shoppingCartService.getShoppingCart();
        String cartTotal = shoppingCartService.getShoppingCartTotal();
        model.addAttribute("total", cartTotal);
        model.addAttribute("shoppingcart", shoppingCart);
        model.addAttribute("allproducts", allProducts);
        return "index";
    }

    @GetMapping("/searchsite")
    public String doSearch(Model model) {
        Hashtable<Products, Integer> shoppingCart = shoppingCartService.getShoppingCart();
        String cartTotal = shoppingCartService.getShoppingCartTotal();
        model.addAttribute("total", cartTotal);
        model.addAttribute("shoppingcart", shoppingCart);

        return "searchpage";
    }

    @PostMapping("/search")
    public String searchProduct(@RequestParam String searchItem, Model model) {
        List<Products> p = productService.findings(searchItem);

        model.addAttribute("allproducts", productService.sortProducts(p, "alphabetical"));

        if (p.isEmpty()) {
            model.addAttribute("noresult", "No match for: '" + searchItem + "'");
        }

        return doSearch(model);
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

    @GetMapping("/clear-cart")
    public String clearCart(Model model) {
        shoppingCartService.clearShoppingCart();

        return "redirect:/";
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
