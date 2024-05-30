package com.indocyber.trollmarket.services;


import com.indocyber.trollmarket.dtos.AuthRegisterDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthService {
    void registerBuyer(AuthRegisterDto dto);
    void registerSeller(AuthRegisterDto dto);
    Boolean checkAccount(AuthRegisterDto dto);

    //    Authentication.
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    boolean isUsernameExist(String inputUsername);
//    UserDetails loadUserByUsernameAndRole(String username, RoleEnum role) throws UsernameNotFoundException;
}
