package com.indocyber.trollmarket.repositories;

import com.indocyber.trollmarket.models.Role;
import com.indocyber.trollmarket.models.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(RoleEnum name);
}
