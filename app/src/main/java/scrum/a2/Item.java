package scrum.a2;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private String name;
    private Long quantity;
    private Double price;
    private final int LIMIT = 15; // quantity cannot go above 15 for each item 
    private String category;

    private Long quantitySold;

    List<String> categories = new ArrayList<>();
 
    Item(String name, double price, Long quantity, String category) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.quantitySold = 0L;

        // initialise the categories list 
        this.categories.add("Drinks");
        this.categories.add("Chocolates");
        this.categories.add("Chips");
        this.categories.add("Candies");

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return this.category;
    }   

    // we only can have 4 categories 
    // same as changing the item category
    public boolean setCategory(String category) {
        String cat = null;
        for (String c: categories) {
            if (category.equalsIgnoreCase(c)) {
                cat = c;
                break;
            }
        }
        if (cat != null) {
            this.category = cat;
            return true;
        } else {    
            // show error message that category is not in database/valid 
            return false;
        }
    }  

    public Long getQuantity() {
        return this.quantity;
    } 

    public boolean setQuantity(Long quantity) {
        if (quantity <= LIMIT) {
            this.quantity = quantity;
            return true;
        }
        else {
            // show error message cannot set quantity above 15
            return false;
        }
    }

    public Long getQuantitySold(){
        return this.quantitySold;
    }

    public void setQuantitySold(Long quantitySold){
        this.quantitySold = quantitySold;
    }

    public boolean addToCart() {
        if (quantity <= 0) {
            return false;
        } else {
            this.quantity--;
            this.quantitySold++;
            return true;
        }
    }

    public void returnToCart() {
        this.quantity++;
        this.quantitySold--;
    }

}
