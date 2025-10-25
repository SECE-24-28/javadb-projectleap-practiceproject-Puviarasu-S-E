package com.cafeteriatracker.service;

import com.cafeteriatracker.model.Sale;
import com.cafeteriatracker.model.User;
import com.cafeteriatracker.model.MenuItem;
import com.cafeteriatracker.repository.SaleRepository;
import com.cafeteriatracker.repository.UserRepository;
import com.cafeteriatracker.repository.MenuItemRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SaleService {
    private final SaleRepository saleRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    
    public SaleService(SaleRepository saleRepository, UserRepository userRepository, MenuItemRepository menuItemRepository) {
        this.saleRepository = saleRepository;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
    }
    
    public Sale saveSale(Sale sale) {
        sale.setTotalAmount(sale.getMenuItem().getPrice().multiply(java.math.BigDecimal.valueOf(sale.getQuantity())));
        return saleRepository.save(sale);
    }
    
    public Sale createSale(Long userId, Long itemId, Integer quantity) {
        User user = userRepository.findById(userId).orElseThrow();
        MenuItem menuItem = menuItemRepository.findById(itemId).orElseThrow();
        
        Sale sale = new Sale();
        sale.setUser(user);
        sale.setMenuItem(menuItem);
        sale.setQuantity(quantity);
        sale.setTotalAmount(menuItem.getPrice().multiply(java.math.BigDecimal.valueOf(quantity)));
        
        return saleRepository.save(sale);
    }
    
    public List<Sale> getSalesByUser(Long userId) {
        return saleRepository.findByUserUserId(userId);
    }
    
    public List<Sale> getSalesByItem(Long itemId) {
        return saleRepository.findByMenuItemItemId(itemId);
    }
    
    public List<Sale> getDailySales(LocalDateTime date) {
        LocalDateTime start = date.toLocalDate().atStartOfDay();
        LocalDateTime end = start.plusDays(1);
        return saleRepository.findByPurchaseDateBetween(start, end);
    }
    
    public List<Sale> getMonthlySales(int year, int month) {
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime end = start.plusMonths(1);
        return saleRepository.findByPurchaseDateBetween(start, end);
    }
    
    public List<Object[]> getPopularItems() {
        return saleRepository.findPopularItems();
    }
    
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }
}