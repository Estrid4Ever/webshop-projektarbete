package com.webshop.webshopprojektarbete.ui;

import com.webshop.webshopprojektarbete.entity.Products;
import com.webshop.webshopprojektarbete.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CheckOutController {
    @Autowired
    CheckOutService checkOutService;
    @Autowired
    OrderService orderService;
    @Autowired
    UsersServiceImpl usersService;
    @Autowired
    ShoppingCartService shoppingCartService;


    @GetMapping("/checkout")
    public String checkout(Model model) {
        String receiver = usersService.getUserId();
        String cartTotal = shoppingCartService.getShoppingCartTotal();
        model.addAttribute("total", cartTotal);
        model.addAttribute("shoppingcart", checkOutService.getShoppingCart());
        model.addAttribute("confirmation_receiver", receiver);
        if (shoppingCartService.getShoppingCart().isEmpty()){
            return "/emptycartredirectpage";
        }
        return "checkoutpage";
    }
    @PostMapping("/placeorder")
    public String placeOrder(Model model){
        orderService.placeNewOrder(usersService.getUserId(), checkOutService.getShoppingCart());
        String verificationEmail = usersService.getUserId();
        model.addAttribute("receiver", verificationEmail);
        //TODO Om kundvagn e tom ska vi inte kunna lägga beställning..
        return "confirmationpage";
    }
    @GetMapping("/add-to-cart-checkout")
    public String addItemToCart(@RequestParam("item-id") String itemId, Model model) {
        shoppingCartService.addProductToCart(Integer.parseInt(itemId));
        return checkout(model);
    }

    @GetMapping("/remove-from-cart-checkout")
    public String removeItemFromCart(@RequestParam("item-id") String itemId,
                                     @RequestParam("amount") String amount, Model model) {

        int removeAmount = Integer.parseInt(amount);

        for (int i = 0; i < removeAmount; i++) {
            shoppingCartService.removeProductFromCart(Integer.parseInt(itemId));
        }
        if (shoppingCartService.getShoppingCart().isEmpty()){
            return "/emptycartredirectpage";
        }
        return checkout(model);
    }
}
