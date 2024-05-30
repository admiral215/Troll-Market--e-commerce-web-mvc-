package com.indocyber.trollmarket.repositories;

import com.indocyber.trollmarket.models.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, BigInteger> {
    @Query("""
            SELECT c
            FROM Cart c
            Where :accountId = c.user.accountId
            """)
    Page<Cart> findByAccountId(Pageable pageable, @RequestParam("accountId") String accountId);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM Carts WHERE UserId = :accountId
            """,nativeQuery = true)
    void deleteByAccountId(@Param("accountId") String accountId);

    @Query("""
            SELECT c
            FROM Cart c
            Where :productId = c.user.accountId
            """)
    Cart findByProductId(@Param("productId") Integer productId);

    @Query("""
            SELECT c
            FROM Cart c
            Where :accountId = c.user.accountId
            AND :productId = c.product.id
            """)
    Optional<Cart> findByAccountIdAndProductId(@Param("productId") Integer productId,
                                              @Param("accountId") String accountId);
}
