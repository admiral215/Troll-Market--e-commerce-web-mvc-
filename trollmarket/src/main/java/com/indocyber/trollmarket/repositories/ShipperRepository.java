package com.indocyber.trollmarket.repositories;

import com.indocyber.trollmarket.models.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShipperRepository extends JpaRepository<Shipper, Integer> {
    @Query("""
            SELECT s
            FROM Shipper s
            WHERE s.isService = true
            """)
    List<Shipper> findAllByService();
}
