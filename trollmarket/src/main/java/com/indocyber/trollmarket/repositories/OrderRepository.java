package com.indocyber.trollmarket.repositories;

import com.indocyber.trollmarket.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;

public interface    OrderRepository extends JpaRepository<Order, BigInteger> {
    @Query("""
            SELECT o
            FROM Order o
            WHERE :accountId = o.user.accountId
            """)
    Page<Order> getAllByBuyerId (Pageable pageable, @RequestParam("accountId") String accountId);

    @Query("""
            SELECT o
            FROM Order o
            WHERE :accountId = o.product.user.accountId
            """)
    Page<Order> getAllBySellerId (Pageable pageable, @RequestParam("accountId") String accountId);

    @Query("""
            SELECT o
            FROM Order o
            WHERE (:sellerId IS NULL OR :sellerId = o.user.accountId )
            AND (:buyerId IS NULL OR :buyerId = o.product.user.accountId)
            """)
    Page<Order> getAllBySearch (Pageable pageable,
                                @RequestParam("buyerId") String buyerId,
                                @RequestParam("sellerId") String sellerId);
}
