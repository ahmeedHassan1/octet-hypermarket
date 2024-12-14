/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hypermarket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 *
 * @author ahmed
 */
public class Seller extends Person {

    private static final Counter counter = new Counter(FilePaths.sellerEmployeeCounterPath);

    public Seller() {
    }

    public Seller(String username, String email, String password, String address, int number) {
        super(username, email, password, address, number);
        counter.increment();
        setId("Seller_" + counter.getValue());

        File file = FileHandler.createFile(FilePaths.sellerEmployeePath);
        FileHandler.writeToFile(file,
                getId() + "," + username + "," + email + "," + password + "," + address + "," + number, true);
    }

    public String[] login(String email, String password) throws Exception {
        String[] employees = FileHandler.readFile(FilePaths.sellerEmployeePath);

        for (String line : employees) {
            String[] details = line.split(",");

            if (details.length > 0 && details[2].equals(email) && details[3].equals(password)) {
                return details;
            }
        }

        throw new Exception("Invalid email or password.");
    }

    @Override
    public int calculateSalary() {
        int baseSalary = super.calculateSalary();
        int salary = baseSalary + 3000;
        return salary;
    }

    public String[] listProducts() {
        return FileHandler.readFile(FilePaths.productsPath);
    }

    public static String searchProduct(int id) {
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

    public void createOrder(int[] productIds, int[] quantities) throws Exception {
        try {
            String[] products = FileHandler.readFile(FilePaths.productsPath);

            for (int i = 0; i < productIds.length; i++) {
                boolean productFound = false;

                for (String line : products) {
                    String[] details = line.split(",");

                    if (details.length > 0 && details[0].equals("Product_" + productIds[i])) {
                        productFound = true;
                        int currentStock = Integer.parseInt(details[2]);

                        if (quantities[i] > currentStock) {
                            throw new Exception("Product_" + productIds[i] + " stock is insufficient.");
                        }
                    }
                }

                if (!productFound) {
                    throw new Exception("Product_" + productIds[i] + " not found.");
                }
            }

            for (int i = 0; i < productIds.length; i++) {
                String productLine = searchProduct(productIds[i]);
                String[] productDetails = productLine.split(",");
                int currentStock = Integer.parseInt(productDetails[2]);

                int newQuantity = currentStock - quantities[i];

                InventoryEmployee.updateProductQuantity(productIds[i], newQuantity);
            }

            new Order(getId(), productIds, quantities);
        } catch (Exception e) {
            throw new Exception("Error creating order: " + e.getMessage());
        }
    }

    public void cancelOrder(String orderId) throws Exception {
        String[] orders = FileHandler.readFile(FilePaths.orderPath);
        boolean found = false;

        for (String line : orders) {
            String[] details = line.split(",");

            if (details.length > 0 && details[0].equals(orderId)) {
                found = true; 
                int[] productIds = new int[details.length - 3];
                int[] quantities = new int[details.length - 3];

                for (int i = 3; i < details.length; i++) {
                    String[] productDetails = details[i].split(":");
                    productIds[i - 3] = Integer.parseInt(productDetails[0]);
                    quantities[i - 3] = Integer.parseInt(productDetails[1]);
                }

                for (int i = 0; i < productIds.length; i++) {
                    String productLine = searchProduct(productIds[i]);
                    if (productLine == null) {
                        throw new Exception("Product with ID " + productIds[i] + " not found.");
                    }
                    String[] productDetails = productLine.split(",");
                    int currentStock = Integer.parseInt(productDetails[2]);

                    int newQuantity = currentStock + quantities[i];

                    InventoryEmployee.updateProductQuantity(productIds[i], newQuantity);
                }

                File inputFile = new File(FilePaths.orderPath);
                File tempFile = new File("tempFile.txt");

                try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

                    String currentLine;

                    while ((currentLine = reader.readLine()) != null) {
                        if (currentLine.equals(line)) {
                            continue; 
                        }

                        writer.write(currentLine);
                        writer.newLine();
                    }
                } catch (Exception e) {
                    throw new Exception("Error canceling order: " + e.getMessage());
                }

                inputFile.delete();
                tempFile.renameTo(inputFile);

                break; 
            }
        }

        if (!found) {
            throw new Exception("Order with ID " + orderId + " not found.");
        }
    }

    public void updateSellerEmployee(String username, String email, String password,
            String address, int number) throws Exception {
        String filePath = FilePaths.sellerEmployeePath;

        File inputFile = new File(filePath);
        File tempFile = new File("tempFile.txt");

        boolean found = false;
        String idString = getId();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String[] details = currentLine.split(",");

                if (details.length > 0 && details[0].equals(idString)) {
                    found = true;
                    details[1] = username;
                    details[2] = email;
                    details[3] = password;
                    details[4] = address;
                    details[5] = number + "";
                    String updatedLine = String.join(",", details);
                    writer.write(updatedLine);
                    writer.newLine();
                } else {
                    writer.write(currentLine);
                    writer.newLine();
                }
            }
        } catch (Exception e) {
            throw new Exception("Error updating employee: " + e.getMessage());
        }

        if (found) {
            inputFile.delete();
            tempFile.renameTo(inputFile);
        } else {
            tempFile.delete();
            throw new Exception("Employee with ID " + idString + " not found.");
        }
    }
}
