package com.indocyber.trollmarket.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@Builder
public class MyAccountDetail implements UserDetails {

    private Account account;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        var grantedAuth = account.roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).toList();
        return account.roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).toList();
    }

    @Override
    public String getPassword() {
        return this.account.getPassword();
    }

    @Override
    public String getUsername() {
        return this.account.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
