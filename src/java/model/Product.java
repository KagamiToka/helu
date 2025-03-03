package model;

import java.util.Date;

public class Product {
    private int id;
    private String name;
    private double price;
    private String description;
    private int stock;
    private Date importDate;

    public Product() {
    }

    public Product(String name, double price, String description, int stock, Date importDate) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.importDate = importDate;
    }

    
    public Product(int id, String name, double price, String description, int stock, Date importDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.importDate = importDate;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public int getStock() { return stock; }
    public Date getImportDate() { return importDate; }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", price=" + price + ", description=" + description + ", stock=" + stock + ", importDate=" + importDate + '}';
    }
    
    public int addNew() {
        return id;
    }
}
