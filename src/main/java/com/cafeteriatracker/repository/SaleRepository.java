package com.cafeteriatracker.repository;

import com.cafeteriatracker.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByUserUserId(Long userId);
    List<Sale> findByMenuItemItemId(Long itemId);
    List<Sale> findByPurchaseDateBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT s.menuItem.name, SUM(s.quantity) FROM Sale s GROUP BY s.menuItem.name ORDER BY SUM(s.quantity) DESC")
    List<Object[]> findPopularItems();
}