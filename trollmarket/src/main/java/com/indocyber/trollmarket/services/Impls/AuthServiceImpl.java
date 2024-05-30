package com.indocyber.trollmarket.services.Impls;

import com.indocyber.trollmarket.dtos.AuthRegisterDto;
import com.indocyber.trollmarket.models.*;
import com.indocyber.trollmarket.repositories.AccountRepository;
import com.indocyber.trollmarket.repositories.RoleRepository;
import com.indocyber.trollmarket.repositories.UserRepository;
import com.indocyber.trollmarket.services.AuthService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    public void registerBuyer(AuthRegisterDto dto) {
        var hashedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());

        Role buyerRole = roleRepository.findByName(RoleEnum.BUYER);
        if (buyerRole == null) {
            throw new EntityNotFoundException("Role Buyer not found");
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Password doesn't match");
        }

        var account = Account.builder()
                .username(dto.getUsername())
                .password(hashedPassword)
                .roles(Collections.singleton(buyerRole))
                .build();
        accountRepository.save(account);

        User user = User.builder()
                .accountId(account.getUsername())
                .name(dto.getName())
                .address(dto.getAddress())
                .account(account)
                .balance(BigDecimal.ZERO)
                .build();
        userRepository.save(user);
    }

    @Override
    public void registerSeller(AuthRegisterDto dto) {
        var hashedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());


        Role buyerRole = roleRepository.findByName(RoleEnum.SELLER);
        if (buyerRole == null) {
            throw new EntityNotFoundException("Role SELLER not found");
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Password doesn't match");
        }

        var account = Account.builder()
                .username(dto.getUsername())
                .password(hashedPassword)
                .roles(Collections.singleton(buyerRole))
                .build();
        accountRepository.save(account);

        User user = User.builder()
                .accountId(account.getUsername())
                .name(dto.getName())
                .address(dto.getAddress())
                .account(account)
                .balance(BigDecimal.ZERO)
                .build();

        userRepository.save(user);
    }

    @Override
    public Boolean checkAccount(AuthRegisterDto dto) {
        if (dto.getPassword().equals(dto.getConfirmPassword()) && userRepository.existsById(dto.getUsername())) {
            return false;
        }
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var account = accountRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return new MyAccountDetail(account);
    }

    @Override
    public boolean isUsernameExist(String inputUsername) {
        return accountRepository.existsById(inputUsername);
    }


}
