package com.indocyber.trollmarket.repositories;


import com.indocyber.trollmarket.models.Order;
import com.indocyber.trollmarket.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("""
            SELECT p
            FROM Product p
            WHERE :accountId = p.user.accountId
            """)
    Page<Product> getAllByAccountId (Pageable pageable, @RequestParam("accountId") String accountId);

    @Query("""
            SELECT p
            FROM Product p
            WHERE (:name IS NULL OR p.name LIKE %:name%)
            AND (:category IS NULL OR p.category LIKE %:category%)
            AND (:description IS NULL OR p.description LIKE %:description%)
            AND p.discontinue = false
            """)
    Page<Product> getAllBySearch (Pageable pageable,
                                  @RequestParam("name") String name,
                                  @RequestParam("category") String category,
                                  @RequestParam("description") String description);


}
