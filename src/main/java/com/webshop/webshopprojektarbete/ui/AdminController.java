package com.webshop.webshopprojektarbete.ui;

import com.webshop.webshopprojektarbete.entity.Order;
import com.webshop.webshopprojektarbete.repository.OrderLineRepo;
import com.webshop.webshopprojektarbete.repository.OrderRepo;
import com.webshop.webshopprojektarbete.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    OrderLineRepo orderLineRepo;
    @Autowired
    OrderService orderService;

    @GetMapping("/allorders")
    public String showAllOrders(Model model){
        //todo lägg LIST i service ??
        List<Order> orderList = orderRepo.findAll();
        model.addAttribute("all_orders", orderList);
        return "allorderspage";
    }
    @PostMapping("/start_handling")
    public String startHandlingOrder(@RequestParam("order_id") int orderId, Model model){
        Order o = orderService.findOrderById(orderId);
        model.addAttribute("order_to_handle", o);
        return "order_management_page";
    }

    @PostMapping("/mark_order")
    public String markOrder(@RequestParam("order_id") int orderId, @RequestParam("action") String action, Model model) {
        String orderStatus = null;
        switch (action) {
            case "PENDING":
                orderStatus = "PENDING";
                break;
            case "SHIPPED":
                orderStatus = "SENT";

                break;
            case "RECEIVED":
                orderStatus = "RECEIVED";
                break;
            case "RETURN":
                return "redirect:/allorders";

        }
        orderService.markOrder(orderId, orderStatus);
        return startHandlingOrder(orderId, model);
    }
    @GetMapping("/admin_start_page")
    public String adminStart(){
        return "admin_start_page";
    }

    @GetMapping("/orders_to_handle")
    public String showOrdersToHandle(Model model){
        List<Order> receivedOrders = orderService.findOrdersByStatus("RECEIVED");
        model.addAttribute("received_orders", receivedOrders);
        return "orders_to_handle";
    }
    @GetMapping("/shipped_orders")
    public String showShippedOrders(Model model){
        List<Order> sentOrders = orderService.findOrdersByStatus("SENT");
        model.addAttribute("sent_orders", sentOrders);
        return "shipped_orders_page";
    }
}
