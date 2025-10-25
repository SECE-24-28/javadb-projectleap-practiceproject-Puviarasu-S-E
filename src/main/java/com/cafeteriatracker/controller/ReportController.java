package com.cafeteriatracker.controller;

import com.cafeteriatracker.model.Sale;
import com.cafeteriatracker.service.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final SaleService saleService;
    
    public ReportController(SaleService saleService) {
        this.saleService = saleService;
    }
    
    @GetMapping("/daily")
    public ResponseEntity<List<Sale>> getDailyReport(@RequestParam String date) {
        LocalDateTime reportDate = LocalDateTime.parse(date + "T00:00:00");
        return ResponseEntity.ok(saleService.getDailySales(reportDate));
    }
    
    @GetMapping("/monthly")
    public ResponseEntity<List<Sale>> getMonthlyReport(@RequestParam int year, @RequestParam int month) {
        return ResponseEntity.ok(saleService.getMonthlySales(year, month));
    }
    
    @GetMapping("/popular-items")
    public ResponseEntity<List<Object[]>> getPopularItems() {
        return ResponseEntity.ok(saleService.getPopularItems());
    }
}