package com.cafeteriatracker.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafeteriatracker.model.Sale;
import com.cafeteriatracker.service.SaleService;

@RestController
@RequestMapping("/api/sales")
public class SaleController {
    private final SaleService saleService;
    
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }
    
    @PostMapping
    public ResponseEntity<Sale> recordSale(@RequestBody Sale sale) {
        return ResponseEntity.ok(saleService.saveSale(sale));
    }
    
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Sale>> getSalesByUser(@PathVariable Long id) {
        return ResponseEntity.ok(saleService.getSalesByUser(id));
    }
    
    @GetMapping("/item/{id}")
    public ResponseEntity<List<Sale>> getSalesByItem(@PathVariable Long id) {
        return ResponseEntity.ok(saleService.getSalesByItem(id));
    }
    
    @GetMapping
    public ResponseEntity<List<Sale>> getAllSales() {
        return ResponseEntity.ok(saleService.getAllSales());
    }
}