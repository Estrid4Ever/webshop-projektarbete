package com.webshop.webshopprojektarbete.securityconfig;

import com.webshop.webshopprojektarbete.entity.Users;
import com.webshop.webshopprojektarbete.service.UsersServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        String password = DigestUtils.sha256Hex(auth.getCredentials()
                .toString());

        Optional<Users> optionalUser = usersService.fetchOptionalUser(username);

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            usersService.setUsers(user);

            if (user.getEmail().equals(username) && user.getPassword().equals(password)) {
                ArrayList<SimpleGrantedAuthority> roles = new ArrayList<>();
                roles.add(new SimpleGrantedAuthority(String.valueOf(user.getIsAdmin())));
                return new UsernamePasswordAuthenticationToken
                        (username, password, roles);
            } else {
                usersService.setUsers(null);
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