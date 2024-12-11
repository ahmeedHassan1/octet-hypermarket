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
        // counter++;
        // setId("Seller_" + counter);
    }

    public Seller(String username, String email, String password, String address, int number) {
        super(username, email, password, address, number);
        counter.increment();
        setId("Seller_" + counter.getValue());

        File file = FileHandler.createFile(FilePaths.sellerEmployeePath);
        FileHandler.writeToFile(file,
                getId() + "," + username + "," + email + "," + password + "," + address + "," + number);
    }

    public String[] listProducts() {
        return FileHandler.readFile(FilePaths.productsPath);
    }

    public String searchProduct(int id) {
        String[] products = FileHandler.readFile(FilePaths.productsPath);

        for (String line : products) {
            String[] details = line.split(",");

            if (details.length > 0 && details[0].startsWith("Product_")) {
                int extractedId = Integer.parseInt(details[0].substring(8));

                if (extractedId == id) {
                    return line;
                }
            }
        }

        return "Product with ID Product_" + id + " not found.";
    }

    // public void createOrder() {
    // }

    // public Order cancelOrder() {
    // }
}
