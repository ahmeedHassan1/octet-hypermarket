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
public class InventoryEmployee extends Person {

    private static final Counter counter = new Counter(FilePaths.inventoryEmployeeCounterPath);

    public InventoryEmployee() {
//        counter++;
//        setId("InventoryEmployee_" + counter);
    }

    public InventoryEmployee(String username, String email, String password, String address, int number) {
        super(username, email, password, address, number);
        counter.increment();
        setId("InventoryEmployee_" + counter.getValue());

        File file = FileHandler.createFile(FilePaths.inventoryEmployeePath);
        FileHandler.writeToFile(file, getId() + "," + username + "," + email + "," + password + "," + address + "," + number);
    }

//    public void addProduct() {
//    }
//
//    public void deleteProduct() {
//    }
//
//    public void updateProduct() {
//    }
//
//    public Product[] listProducts() {
//    }
//
//    public Product searchProduct() {
//    }
//
//    public String quantityOfProductNotification() {
//    }
//
//    public String expiryDateOfProductNotification() {
//    }
}
