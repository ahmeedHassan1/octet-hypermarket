/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hypermarket;

import java.io.File;

/**
 *
 * @author ahmed
 */
public class Seller extends Person {

    private static final Counter counter = new Counter(FilePaths.sellerEmployeeCounterPath);

    public Seller() {
//        counter++;
//        setId("Seller_" + counter);
    }

    public Seller(String username, String email, String password, String address, int number) {
        super(username, email, password, address, number);
        counter.increment();
        setId("Seller_" + counter.getValue());

        File file = FileHandler.createFile(FilePaths.sellerEmployeePath);
        FileHandler.writeToFile(file, getId() + "," + username + "," + email + "," + password + "," + address + "," + number);
    }

//    public Product[] listProducts() {
//    }
//
//    public Product searchProduct() {
//    }
//
//    public void createOrder() {
//    }
//
//    public Order cancelOrder() {
//    }
}
