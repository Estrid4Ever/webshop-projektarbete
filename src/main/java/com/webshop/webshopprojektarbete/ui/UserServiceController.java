package com.webshop.webshopprojektarbete.ui;

import com.webshop.webshopprojektarbete.Status;
import com.webshop.webshopprojektarbete.entity.Users;
import com.webshop.webshopprojektarbete.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.function.ToDoubleBiFunction;


@Controller
public class UserServiceController {
    @Autowired
    UserService userservice;

    @PostMapping("newUserMVC") //url fråm formulär
    public String adderMethod(@RequestParam String name,//från formulär
                              @RequestParam String lastname,//från formulär
                              @RequestParam String email,//från formulär
                              @RequestParam String password,//från formulär
                               Model model){//vi ber om detta
        if (userservice.isRegistered(email)) {
            // TODO: 2024-03-11 om användaren redan finns registrerad, ge felmeddelande och skicka till loggin-sidan
            model.addAttribute("validationstatus", "Email already registered, please log in.");
            return "loginsite";
        }
        if (userservice.checkEmailFormat(email)){
            userservice.saveUser(new Users(email,name,lastname,password));
            model.addAttribute("userinfo", email);
            return "verifypage";
        } else {
            model.addAttribute("wrong_email_format", "Wrong email format");
            return "addnewuser";
        }
        // TODO här skicka vidare felformattext


    }
    @PostMapping("/validateUserLogin") //url fråm formulär
    public String userLogin(@RequestParam String email,//från formulär
                            @RequestParam String password,//från formulär
                            Model model){//vi ber om detta
        Status validate = userservice.validateLogin(email,password);

        switch (validate){
            case USER_NOT_REGISTERED -> {
                model.addAttribute("validationstatus", "Not registered!");
                return "loginsite";
            }
            case USER_IS_NOT_ENABLED -> {
                model.addAttribute("userinfo", email);
                return "verifypage";
            }
            case USER_IS_ENABLED -> {
                return "verified";
            }
            case WRONG_DETAILS -> {
                model.addAttribute("validationstatus", "Wrong details");
                return "loginsite";
            }
        }


        return email;
    }
    @GetMapping("loginsite")
    public String login(){
        return "loginsite";
    }

    @PostMapping("verifyuser")
    public String verifyMethod(@RequestParam("token") String token, Model model){
        if (userservice.verifyToken(token)){
            return "verified";
        } else {
            return "notverified";
        }

    }
    @GetMapping("/")
    public String doGet() {
        return "index";
    }
}
