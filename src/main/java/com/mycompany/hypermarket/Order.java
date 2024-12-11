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
public class Order {

    private static final Counter counter = new Counter(FilePaths.orderCounterPath);
    private String id;
    private String owner;
    private Date createdAt;
    private int[] products;
    private int[] quantities;

    public Order(String owner, int[] products, int[] quantities) throws Exception {
        if (products.length != quantities.length) {
            throw new Exception("Products and quantities arrays must have the same length.");
        }

        this.owner = owner;
        this.products = products;
        this.quantities = quantities;
        this.createdAt = new Date();

        counter.increment();
        setId("Order_" + counter.getValue());

        StringBuilder orderDetails = new StringBuilder();
        orderDetails.append(getId()).append(",").append(owner).append(",").append(createdAt);

        for (int i = 0; i < products.length; i++) {
            orderDetails.append(",").append(products[i]).append(":").append(quantities[i]);
        }

        File file = FileHandler.createFile(FilePaths.orderPath);
        FileHandler.writeToFile(file, orderDetails.toString());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
