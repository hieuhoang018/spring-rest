package com.hieuhoang.springrest.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "products")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank @Column(nullable = false)
    private String name;


    @Min(0)
    private double price;


    @Column(length = 1000)
    private String description;


    public Product() {}


    public Product(String name, double price, String description) {
        this.name = name; this.price = price; this.description = description;
    }


    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
