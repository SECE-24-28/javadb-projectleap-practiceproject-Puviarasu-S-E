package com.cafeteriatracker.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Simple MenuItem class without JPA annotations
class MenuItem {
    private Long itemId;
    private String name;
    private String category;
    private BigDecimal price;
    private Boolean availability = true;
    private LocalDateTime addedDate = LocalDateTime.now();
    
    public MenuItem() {}
    
    public MenuItem(String name, String category, BigDecimal price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }
    
    // Getters and Setters
    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Boolean getAvailability() { return availability; }
    public void setAvailability(Boolean availability) { this.availability = availability; }
    public LocalDateTime getAddedDate() { return addedDate; }
    public void setAddedDate(LocalDateTime addedDate) { this.addedDate = addedDate; }
    
    @Override
    public String toString() {
        return "MenuItem{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", availability=" + availability +
                ", addedDate=" + addedDate +
                '}';
    }
}

// Simple MenuItemService without Spring annotations
class MenuItemService {
    private List<MenuItem> menuItems = new ArrayList<>();
    private Long nextId = 1L;
    
    public MenuItem saveMenuItem(MenuItem menuItem) {
        menuItem.setItemId(nextId++);
        menuItems.add(menuItem);
        return menuItem;
    }
    
    public List<MenuItem> getAllMenuItems() {
        return new ArrayList<>(menuItems);
    }
}

// Simple controller without Spring annotations
public class SimpleMenuItemController {
    private final MenuItemService menuItemService;
    
    public SimpleMenuItemController() {
        this.menuItemService = new MenuItemService();
    }
    
    public MenuItem addMenuItem(MenuItem menuItem) {
        return menuItemService.saveMenuItem(menuItem);
    }
    
    public List<MenuItem> getAllMenuItems() {
        return menuItemService.getAllMenuItems();
    }
    
    public static void main(String[] args) {
        SimpleMenuItemController controller = new SimpleMenuItemController();
        
        // Test adding menu items
        MenuItem item1 = new MenuItem("Pizza", "Main Course", new BigDecimal("12.99"));
        MenuItem item2 = new MenuItem("Burger", "Main Course", new BigDecimal("8.99"));
        MenuItem item3 = new MenuItem("Coffee", "Beverage", new BigDecimal("3.99"));
        
        System.out.println("Adding menu items...");
        controller.addMenuItem(item1);
        controller.addMenuItem(item2);
        controller.addMenuItem(item3);
        
        System.out.println("\nAll menu items:");
        List<MenuItem> allItems = controller.getAllMenuItems();
        for (MenuItem item : allItems) {
            System.out.println(item);
        }
    }
}