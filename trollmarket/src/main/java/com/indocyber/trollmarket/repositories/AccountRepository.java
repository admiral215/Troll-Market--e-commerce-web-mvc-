package com.indocyber.trollmarket.repositories;


import com.indocyber.trollmarket.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {

}
