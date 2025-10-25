package com.cafeteriatracker.controller;

import com.cafeteriatracker.model.MenuItem;
import com.cafeteriatracker.model.Sale;
import com.cafeteriatracker.model.User;
import com.cafeteriatracker.service.MenuItemService;
import com.cafeteriatracker.service.SaleService;
import com.cafeteriatracker.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

@Controller
public class WebController {
    private final UserService userService;
    private final MenuItemService menuItemService;
    private final SaleService saleService;
    
    public WebController(UserService userService, MenuItemService menuItemService, SaleService saleService) {
        this.userService = userService;
        this.menuItemService = menuItemService;
        this.saleService = saleService;
    }
    
    @GetMapping("/")
    public String index(Model model) {
        // Get dashboard statistics
        List<User> users = userService.getAllUsers();
        List<MenuItem> menuItems = menuItemService.getAllMenuItems();
        List<Sale> sales = saleService.getAllSales();
        
        // Calculate statistics
        model.addAttribute("totalUsers", users.size());
        model.addAttribute("totalMenuItems", menuItems.size());
        model.addAttribute("totalSales", sales.size());
        
        // Calculate total revenue
        double totalRevenue = sales.stream()
            .mapToDouble(sale -> sale.getTotalAmount().doubleValue())
            .sum();
        model.addAttribute("totalRevenue", totalRevenue);
        
        return "index";
    }
    
    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }
    
    @PostMapping("/users")
    public String addUser(@ModelAttribute User user, Model model) {
        try {
            userService.saveUser(user);
            model.addAttribute("success", "Customer added successfully!");
        } catch (Exception e) {
            if (e.getMessage().contains("Unique index or primary key violation")) {
                model.addAttribute("error", "Email already exists. Please use a different email address.");
            } else {
                model.addAttribute("error", "Error adding customer: " + e.getMessage());
            }
        }
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }
    
    @GetMapping("/menu")
    public String menu(Model model) {
        model.addAttribute("menuItems", menuItemService.getAllMenuItems());
        return "menu";
    }
    
    @PostMapping("/menu")
    public String addMenuItem(@ModelAttribute MenuItem menuItem, Model model) {
        try {
            menuItemService.saveMenuItem(menuItem);
            model.addAttribute("success", "Menu item added successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Error adding menu item: " + e.getMessage());
        }
        model.addAttribute("menuItems", menuItemService.getAllMenuItems());
        return "menu";
    }
    
    @GetMapping("/sales")
    public String sales(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("menuItems", menuItemService.getAllMenuItems());
        model.addAttribute("sales", saleService.getAllSales());
        return "sales";
    }
    
    @PostMapping("/sales")
    public String recordSale(@RequestParam Long userId, @RequestParam Long itemId, @RequestParam Integer quantity, Model model) {
        try {
            saleService.createSale(userId, itemId, quantity);
            model.addAttribute("success", "Sale recorded successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Error recording sale: " + e.getMessage());
        }
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("menuItems", menuItemService.getAllMenuItems());
        model.addAttribute("sales", saleService.getAllSales());
        return "sales";
    }
    
    @GetMapping("/reports")
    public String reports(Model model) {
        model.addAttribute("popularItems", saleService.getPopularItems());
        return "reports";
    }
    
    @GetMapping("/reports/daily")
    public String dailyReport(@RequestParam String date, Model model) {
        try {
            LocalDateTime reportDate = LocalDateTime.parse(date + "T00:00:00");
            model.addAttribute("reportData", saleService.getDailySales(reportDate));
            model.addAttribute("success", "Daily report generated for " + date);
        } catch (Exception e) {
            model.addAttribute("error", "Error generating daily report: " + e.getMessage());
        }
        model.addAttribute("popularItems", saleService.getPopularItems());
        return "reports";
    }
    
    @GetMapping("/reports/monthly")
    public String monthlyReport(@RequestParam int year, @RequestParam int month, Model model) {
        try {
            model.addAttribute("reportData", saleService.getMonthlySales(year, month));
            model.addAttribute("success", "Monthly report generated for " + month + "/" + year);
        } catch (Exception e) {
            model.addAttribute("error", "Error generating monthly report: " + e.getMessage());
        }
        model.addAttribute("popularItems", saleService.getPopularItems());
        return "reports";
    }
}