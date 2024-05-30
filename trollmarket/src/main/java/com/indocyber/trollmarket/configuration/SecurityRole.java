package com.indocyber.trollmarket.configuration;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityRole {
    public static void updateAuthorities(String username, String newRoles) {
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();

        if (currentAuth != null && currentAuth.getName().equals(username)) {
            List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<>();
            updatedAuthorities.add(new SimpleGrantedAuthority(newRoles));

            Authentication newAuth = new UsernamePasswordAuthenticationToken(
                    currentAuth.getPrincipal(),
                    currentAuth.getCredentials(),
                    updatedAuthorities
            );

            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
    }

}
