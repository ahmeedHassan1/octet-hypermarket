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
    }

    public InventoryEmployee(String username, String email, String password, String address, int number) {
        super(username, email, password, address, number);
        counter.increment();
        setId("InventoryEmployee_" + counter.getValue());

        File file = FileHandler.createFile(FilePaths.inventoryEmployeePath);
        FileHandler.writeToFile(file,
                getId() + "," + username + "," + email + "," + password + "," + address + "," + number, true);
    }

    public String[] login(String email, String password) throws Exception {
        String[] employees = FileHandler.readFile(FilePaths.inventoryEmployeePath);

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
        int salary = baseSalary + 4000;
        return salary;
    }

    public void addProduct(String name, int quantity, double price, Date expiryDate) {
        new Product(name, quantity, price, expiryDate);
    }

    public void deleteProduct(int id) throws Exception {
        String filePath = FilePaths.productsPath;

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
            throw new Exception("Error deleting product: " + e.getMessage());
        }

        if (found) {
            inputFile.delete();
            tempFile.renameTo(inputFile);
        } else {
            tempFile.delete();
            throw new Exception("Product with ID " + idString + " not found.");
        }
    }

    public void updateProductPrice(int idToUpdate, double newPrice) throws Exception{
        String filePath = FilePaths.productsPath;

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
            throw new Exception("Error updating price: " + e.getMessage());
        }

        if (found) {
            inputFile.delete();
            tempFile.renameTo(inputFile);
        } else {
            tempFile.delete();
            throw new Exception("Product with ID " + idString + " not found.");
        }
    }

    public static void updateProductQuantity(int idToUpdate, int newQuantity) throws Exception {
        String filePath = FilePaths.productsPath;

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
            throw new Exception("Error updating quantity: " + e.getMessage());
        }

        if (found) {
            inputFile.delete();
            tempFile.renameTo(inputFile);
        } else {
            tempFile.delete();
            throw new Exception("Product with ID " + idString + " not found.");
        }
    }

    public String[] listProducts() {
        return FileHandler.readFile(FilePaths.productsPath);
    }

    public String searchProduct(int id) throws Exception {
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

        throw new Exception("Product with ID Product_" + id + " not found.");
    }

    public String quantityOfProductNotification(int id) throws Exception {
        String product = searchProduct(id);
        String[] details = product.split(",");
        int quantity = Integer.parseInt(details[2]);
        if (quantity < 10) {
            return "Product with ID Product_" + id + " has a quantity less than 10.";
        }
        return "Product with ID Product_" + id + " has a quantity of " + quantity + ".";
    }

    public String expiryDateOfProductNotification(int id) throws Exception {
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
            throw new Exception("Error parsing date: " + e.getMessage());
        }
    }

    public void updateInventoryEmployee(String username, String email, String password,
            String address, int number) throws Exception {
        String filePath = FilePaths.inventoryEmployeePath;

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
