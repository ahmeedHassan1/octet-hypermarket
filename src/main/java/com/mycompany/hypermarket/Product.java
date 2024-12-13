/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hypermarket;

import java.io.File;
import java.util.Date;

/**
 *
 * @author ahmed
 */
public class Product {

    private static final Counter counter = new Counter(FilePaths.productsCounterPath);
    private String id;
    private String name;
    private int quantity;
    private double price;
    private double offer;
    private Date expiryDate;

    public Product(String name, int quantity, double price, Date expiryDate) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.expiryDate = expiryDate;

        counter.increment();
        setId("Product_" + counter.getValue());

        File file = FileHandler.createFile(FilePaths.productsPath);
        FileHandler.writeToFile(file,
                getId() + "," + name + "," + quantity + "," + price + "," + expiryDate + "," + offer, true);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getOffer() {
        return offer;
    }

    public void setOffer(double offer) {
        this.offer = offer;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

}
