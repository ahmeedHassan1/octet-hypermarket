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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ahmed
 */
public class InventoryEmployee extends Person {

    private static final Counter counter = new Counter(FilePaths.inventoryEmployeeCounterPath);

    public InventoryEmployee() {
        // counter++;
        // setId("InventoryEmployee_" + counter);
    }

    public InventoryEmployee(String username, String email, String password, String address, int number) {
        super(username, email, password, address, number);
        counter.increment();
        setId("InventoryEmployee_" + counter.getValue());

        File file = FileHandler.createFile(FilePaths.inventoryEmployeePath);
        FileHandler.writeToFile(file,
                getId() + "," + username + "," + email + "," + password + "," + address + "," + number, true);
    }

    public static void addProduct(String name, int quantity, double price, Date expiryDate) {
        new Product(name, quantity, price, expiryDate);
    }

    public static void deleteProduct(int id) {
        String filePath = FilePaths.productsPath;
        if (filePath == null) {
            System.out.println("Invalid.");
            return;
        }

        File inputFile = new File(filePath);
        File tempFile = new File("tempFile.txt");

        boolean found = false;
        String idString = "Product" + "_" + id;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String[] details = currentLine.split(",");

                if (details.length > 0 && details[0].equals(idString)) {
                    found = true;
                } else {
                    writer.write(currentLine);
                    writer.newLine();
                }
            }
        } catch (Exception e) {
            System.out.println("Error deleting product: " + e.getMessage());
            return;
        }

        if (found) {
            inputFile.delete();
            tempFile.renameTo(inputFile);
            System.out.println("Product deleted successfully.");

        } else {
            System.out.println("Product with ID " + idString + " not found.");
            tempFile.delete();
        }
    }

    public static void updateProductPrice(int idToUpdate, double newPrice) {
        String filePath = FilePaths.productsPath;
        if (filePath == null) {
            System.out.println("Invalid.");
            return;
        }

        File inputFile = new File(filePath);
        File tempFile = new File("tempFile.txt");

        boolean found = false;
        String idString = "Product" + "_" + idToUpdate;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String[] details = currentLine.split(",");

                if (details.length > 0 && details[0].equals(idString)) {
                    found = true;
                    details[3] = newPrice + "";
                    String updatedLine = String.join(",", details);
                    writer.write(updatedLine);
                    writer.newLine();
                } else {
                    writer.write(currentLine);
                    writer.newLine();
                }
            }
        } catch (Exception e) {
            System.out.println("Error updating product: " + e.getMessage());
            return;
        }

        if (found) {
            inputFile.delete();
            tempFile.renameTo(inputFile);
            System.out.println("Price updated successfully.");

        } else {
            System.out.println("Product with ID " + idString + " not found.");
            tempFile.delete();
        }
    }

    public static void updateProductQuantity(int idToUpdate, int newQuantity) {
        String filePath = FilePaths.productsPath;
        if (filePath == null) {
            System.out.println("Invalid.");
            return;
        }

        File inputFile = new File(filePath);
        File tempFile = new File("tempFile.txt");

        boolean found = false;
        String idString = "Product" + "_" + idToUpdate;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String[] details = currentLine.split(",");

                if (details.length > 0 && details[0].equals(idString)) {
                    found = true;
                    details[2] = newQuantity + "";
                    String updatedLine = String.join(",", details);
                    writer.write(updatedLine);
                    writer.newLine();
                } else {
                    writer.write(currentLine);
                    writer.newLine();
                }
            }
        } catch (Exception e) {
            System.out.println("Error updating product: " + e.getMessage());
            return;
        }

        if (found) {
            inputFile.delete();
            tempFile.renameTo(inputFile);
            System.out.println("Quantity updated successfully.");

        } else {
            System.out.println("Product with ID " + idString + " not found.");
            tempFile.delete();
        }
    }

    public static String[] listProducts() {
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

    public static String quantityOfProductNotification(int id) {
        String product = searchProduct(id);
        String[] details = product.split(",");
        int quantity = Integer.parseInt(details[2]);
        if (quantity < 10) {
            return "Product with ID Product_" + id + " has a quantity less than 10.";
        }
        return "Product with ID Product_" + id + " has a quantity of " + quantity + ".";
    }

    public static String expiryDateOfProductNotification(int id) {
        String product = searchProduct(id);

        try {
            String[] details = product.split(",");

            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
            Date expiryDate = dateFormat.parse(details[4]);
            Date currentDate = new Date();

            if (expiryDate.before(currentDate)) {
                return "Product with ID Product_" + id + " has expired.";
            } else {
                return "Product with ID Product_" + id + " has not expired.";
            }
        } catch (ParseException e) {
            return "Error parsing expiry date for Product_" + id + ": " + e.getMessage();
        }
    }
}
