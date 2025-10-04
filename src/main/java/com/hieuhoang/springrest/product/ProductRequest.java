package com.hieuhoang.springrest.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;


public class ProductRequest {
    @NotBlank(message = "Name is required")
    private String name;


    @Min(value = 0, message = "Price must be >= 0")
    private double price;


    private String description;


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
