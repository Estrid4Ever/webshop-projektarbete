package com.webshop.webshopprojektarbete.ui;

import com.webshop.webshopprojektarbete.Status;
import com.webshop.webshopprojektarbete.entity.Users;
import com.webshop.webshopprojektarbete.service.UserService;
import com.webshop.webshopprojektarbete.service.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserServiceController {
    @Autowired
    UserService userservice;
    @Autowired
    UsersServiceImpl usersService;

    @PostMapping("/newUserMVC") //url fråm formulär
    public String adderMethod(@RequestParam String name,//från formulär
                              @RequestParam String lastname,//från formulär
                              @RequestParam String email,//från formulär
                              @RequestParam String password,//från formulär
                               Model model){//vi ber om detta
        System.out.println("newUserMVC");

        if (userservice.isRegistered(email)) {
            // TODO: 2024-03-11 om användaren redan finns registrerad, ge felmeddelande och skicka till loggin-sidan
            model.addAttribute("validationstatus", "Email already registered, please log in.");
            return "loginpage";
        }
        if (userservice.checkEmailFormat(email)){
            userservice.saveUser(new Users(email,name,lastname,password));
            model.addAttribute("userinfo", email);
            return "verifypage";
        } else {
            model.addAttribute("wrong_email_format", "Wrong email format");
            return "addnewuserpage";
        }
        // TODO här skicka vidare felformattext


    }
    @PostMapping("/validateUserLogin") //url fråm formulär
    public String userLogin(@RequestParam("username") String email,//från formulär
                            @RequestParam String password,//från formulär
                            Model model){//vi ber om detta
        System.out.println("validateuserlogin");
        Status validate = userservice.validateLogin(email,password);
        // TODO: 2024-03-29 Användaren kan för tillfället skita i att verifiera sin mailadress 

        switch (validate){
            case USER_NOT_REGISTERED -> {
                model.addAttribute("validationstatus", "Not registered!");
                return "loginpage";
            }
            case USER_IS_NOT_ENABLED -> { //skickar ny verifieringskod till användaren
                model.addAttribute("userinfo", email);
                return "verifypage";
            }
            case USER_IS_ENABLED -> {
                return "verified";
            }
            case WRONG_DETAILS -> {
                model.addAttribute("validationstatus", "Wrong details");
                return "loginpage";
            }
        }

        return email;
    }
    @GetMapping("loginsite")
    public String login(){
        return "loginpage";
    }

    @GetMapping("addnewuser")
    public String indexSite(){
        return "addnewuserpage";
    }

    @PostMapping("verifyuser")
    public String verifyMethod(@RequestParam("token") String token, Model model){
        if (userservice.verifyToken(token)){
            return "verified";
        } else {
            return "notverified";
        }
    }
}
