package com.webshop.webshopprojektarbete.service;

import com.webshop.webshopprojektarbete.Status;
import com.webshop.webshopprojektarbete.entity.Users;

public interface UserService {
    Status saveUser(Users users);

    boolean checkEmailFormat(String email);

    Status validateLogin(String email, String password);

    Status checkIfExist(String email);

    Boolean verifyToken(String token);
    boolean isRegistered(String email);

    void sendNewToken(String email, String userId);

}
