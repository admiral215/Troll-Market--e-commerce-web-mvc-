package com.indocyber.trollmarket.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/resources/**").permitAll()
                                .requestMatchers( "/register-buyer", "/register-seller", "/login").anonymous()
                                .requestMatchers(  "/role","/home").hasAnyAuthority("ADMINISTRATOR", "BUYER", "SELLER")
                                .requestMatchers("/profile").hasAnyAuthority("BUYER","SELLER")
                                .requestMatchers("/merchandise").hasAuthority("SELLER")
                                .requestMatchers("/cart").hasAuthority("BUYER")
                                .requestMatchers("/shipment").hasAuthority("ADMINISTRATOR")
                                .requestMatchers("/history", "/admin").hasAuthority("ADMINISTRATOR")
                                .anyRequest().authenticated()
                ).formLogin(login -> login
                                .loginPage("/login")
                                .loginProcessingUrl("/authenticating")
                                .defaultSuccessUrl("/role")
                                .successHandler((request, response, authentication) -> response.sendRedirect("/role"))
                                .failureUrl("/login?error=true")
                ).logout(logout -> logout
                        .logoutUrl("/logout"));

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
