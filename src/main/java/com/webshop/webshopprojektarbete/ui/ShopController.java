package com.webshop.webshopprojektarbete.ui;

import com.webshop.webshopprojektarbete.entity.Products;
import com.webshop.webshopprojektarbete.service.ProductService;
import com.webshop.webshopprojektarbete.service.ShoppingCartService;
import com.webshop.webshopprojektarbete.service.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
        Hashtable<Products, Integer> shoppingCart = shoppingCartService.getShoppingCart();
        String cartTotal = shoppingCartService.getShoppingCartTotal();
        model.addAttribute("total", cartTotal);
        model.addAttribute("shoppingcart", shoppingCart);
        return "index :: #cartcontent";
    }

    /*@GetMapping("/add-to-cart")
    public String addItemToCart(@RequestParam("item-id") String itemId, Model model) {
        shoppingCartService.addProductToCart(Integer.parseInt(itemId));
        return "redirect:/";
    }*/

    @RequestMapping(value="/add-to-cart", method=RequestMethod.GET)
    public String removeFromCart(@RequestParam("item-id") String itemId, ModelMap map) {
        shoppingCartService.addProductToCart(Integer.parseInt(itemId));
        Hashtable<Products, Integer> shoppingCart = shoppingCartService.getShoppingCart();
        String cartTotal = shoppingCartService.getShoppingCartTotal();
        map.addAttribute("total", cartTotal);
        map.addAttribute("shoppingcart", shoppingCart);
        return "index :: #cartcontent";
    }

    @RequestMapping(value="/remove-from-cart", method=RequestMethod.GET)
    public String removeFromCart(@RequestParam("item-id") String itemId,
                                 @RequestParam("amount") String amount, ModelMap map) {

        int removeAmount = Integer.parseInt(amount);

        for (int i = 0; i < removeAmount; i++) {
            shoppingCartService.removeProductFromCart(Integer.parseInt(itemId));
        }
        Hashtable<Products, Integer> shoppingCart = shoppingCartService.getShoppingCart();
        String cartTotal = shoppingCartService.getShoppingCartTotal();
        map.addAttribute("total", cartTotal);
        map.addAttribute("shoppingcart", shoppingCart);
        return "index :: #cartcontent";
    }

}
