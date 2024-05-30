package com.indocyber.trollmarket.services.Impls;

import com.indocyber.trollmarket.dtos.admin.AdminRegisterDto;
import com.indocyber.trollmarket.models.Account;
import com.indocyber.trollmarket.models.Role;
import com.indocyber.trollmarket.models.RoleEnum;
import com.indocyber.trollmarket.repositories.AccountRepository;
import com.indocyber.trollmarket.repositories.RoleRepository;
import com.indocyber.trollmarket.services.AdminService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AdminServiceImpl implements AdminService {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    public AdminServiceImpl(AccountRepository accountRepository, RoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void registerAdmin(AdminRegisterDto dto) {
        var hashedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());

        Role adminRole = roleRepository.findByName(RoleEnum.ADMINISTRATOR);
        if (adminRole == null) {
            throw new EntityNotFoundException("Role Administrator not found");
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Password doesn't match");
        }

        var account = Account.builder()
                .username(dto.getUsername())
                .password(hashedPassword)
                .roles(Collections.singleton(adminRole))
                .build();
        accountRepository.save(account);
    }
}
