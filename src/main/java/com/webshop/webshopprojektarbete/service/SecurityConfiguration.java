package com.webshop.webshopprojektarbete.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration{

    @Autowired
    CustomAuthenticationProvider customAuthProvider;


    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(customAuthProvider);


        if (customAuthProvider.getUser() != null) {
            authenticationManagerBuilder.inMemoryAuthentication()
                    .withUser(customAuthProvider.getUser().getEmail())
                    .password(passwordEncoder().encode(customAuthProvider.getUser().getPassword()))
                    .roles(String.valueOf(customAuthProvider.getUser().getIsAdmin()));
        }
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {

        http
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/loginsite").permitAll()
                                .requestMatchers("/addnewuser").permitAll()
                                .requestMatchers("/newUserMVC").permitAll()
                                .requestMatchers("/validateUserLogin").permitAll()
                                .requestMatchers("/admin").hasAuthority("1")//lägg till samtliga adminsidor så här
                                .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/loginsite") // Specify the URL of the custom login page
                        .permitAll()
                        .loginProcessingUrl("/login")// Specify the custom login processing URL
                        .successHandler(new CustomAuthenticationSuccessHandler()) // Specify the success URL after successful login
                )
                .authenticationManager(authManager);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}