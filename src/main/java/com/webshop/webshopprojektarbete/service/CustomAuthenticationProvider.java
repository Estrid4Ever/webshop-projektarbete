package com.webshop.webshopprojektarbete.service;

import com.webshop.webshopprojektarbete.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.Optional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UsersServiceImpl usersService;

    private Users user;

    public Users getUser() {
        return user;
    }

    @Override
    public Authentication authenticate(Authentication auth)
            throws AuthenticationException {
        String username = auth.getName();
        String password = auth.getCredentials()
                .toString();

        Optional<Users> optionalUser = usersService.fetchOptionalUser(username);

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            usersService.setUsers(user);

            if (user.getEmail().equals(username) && user.getPassword().equals(password)) {
                return new UsernamePasswordAuthenticationToken
                        (username, password, Collections.emptyList());
            } else {
                throw new
                        BadCredentialsException("Authentication failed");
            }
        } else {
            throw new
                    BadCredentialsException("Email not registered");
        }
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}