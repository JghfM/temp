package com.example.demo.repository;

import com.example.demo.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IPriceRepo extends JpaRepository<Price, Long > {
    @Query("SELECT p FROM Price p WHERE p.brandId = :brandId AND p.productId = :productId AND p.startDate <= :date AND p.endDate >= :date ORDER BY p.priority DESC")
    List<Price> findByCriteria( @Param("brandId") Long brandId,  @Param("productId") Long productId,@Param("date") LocalDateTime startDate);
}
