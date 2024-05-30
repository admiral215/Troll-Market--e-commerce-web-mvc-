package com.indocyber.trollmarket.repositories;

import com.indocyber.trollmarket.models.RoleEnum;
import com.indocyber.trollmarket.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    @Query("""
            SELECT u
            FROM User u
            JOIN u.account.roles r
            WHERE r.name = :role
            """)
    List<User> findByRole(@Param("role") RoleEnum role);
}
