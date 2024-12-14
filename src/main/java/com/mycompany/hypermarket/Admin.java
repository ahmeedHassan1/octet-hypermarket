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
public class Admin extends Person {

    private static final Counter counter = new Counter(FilePaths.adminCounterPath);

    public Admin() {
    }

    public Admin(String username, String email, String password, String address, int number) {
        super(username, email, password, address, number);
        counter.increment();
        setId("Admin_" + counter.getValue());

        File file = FileHandler.createFile(FilePaths.adminPath);
        FileHandler.writeToFile(file,
                getId() + "," + username + "," + email + "," + password + "," + address + "," + number, true);

    }

    public String[] login(String email, String password) throws Exception {
        String[] admins = FileHandler.readFile(FilePaths.adminPath);

        for (String line : admins) {
            String[] details = line.split(",");

            if (details.length > 0 && details[2].equals(email) && details[3].equals(password)) {
                return details;
            }
        }

        throw new Exception("Invalid email or password");
    }

    @Override
    public int calculateSalary() {
        int baseSalary = super.calculateSalary();
        int salary = baseSalary + 5000;
        return salary;
    }

    public void setEmployeeType(String id, String newEmployeeType) throws Exception {
        String filePath = null;
        if (id.startsWith("Seller_")) {
            filePath = FilePaths.sellerEmployeePath;
        } else if (id.startsWith("MarketingEmployee_")) {
            filePath = FilePaths.marketingEmployeePath;
        } else if (id.startsWith("InventoryEmployee_")) {
            filePath = FilePaths.inventoryEmployeePath;
        } else {
            throw new Exception("Invalid employee ID");
        }

        String[] details = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.startsWith(id)) {
                    details = currentLine.split(",");
                    break;
                }
            }
        } catch (Exception e) {
            throw new Exception("Error updating employee type: " + e.getMessage());
        }

        if (details == null) {
            throw new Exception("Employee with ID " + id + " not found");
        }

        String username = details[1];
        String email = details[2];
        String password = details[3];
        String address = details[4];
        int number = Integer.parseInt(details[5]);

        switch (newEmployeeType.toLowerCase()) {
            case "marketingemployee":
                addMarketingEmployee(username, email, password, address, number);
                break;
            case "seller":
                addSellerEmployee(username, email, password, address, number);
                break;
            case "inventoryemployee":
                addInventoryEmployee(username, email, password, address, number);
                break;
            default:
                throw new Exception("Invalid new employee type");
        }

        if (id.startsWith("Seller_")) {
            deleteSellerEmployee(Integer.parseInt(id.substring(7)));
        } else if (id.startsWith("MarketingEmployee_")) {
            deleteMarketingEmployee(Integer.parseInt(id.substring(18)));
        } else if (id.startsWith("InventoryEmployee_")) {
            deleteInventoryEmployee(Integer.parseInt(id.substring(18)));
        }
    }

    public void updateEmployeeUsername(String employeeType, int idToUpdate, String newUsername) throws Exception {
        String filePath;
        switch (employeeType.toLowerCase()) {
            case "marketingemployee":
                filePath = FilePaths.marketingEmployeePath;
                break;
            case "seller":
                filePath = FilePaths.sellerEmployeePath;
                break;
            case "inventoryemployee":
                filePath = FilePaths.inventoryEmployeePath;
                break;
            default:
                throw new Exception("Invalid employee type");
        }

        File inputFile = new File(filePath);
        File tempFile = new File("tempFile.txt");

        boolean found = false;
        String idString = employeeType + "_" + idToUpdate;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String[] details = currentLine.split(",");

                if (details.length > 0 && details[0].equals(idString)) {
                    found = true;
                    details[1] = newUsername;
                    String updatedLine = String.join(",", details);
                    writer.write(updatedLine);
                    writer.newLine();
                } else {
                    writer.write(currentLine);
                    writer.newLine();
                }
            }
        } catch (Exception e) {
            throw new Exception("Error updating employee username: " + e.getMessage());
        }

        if (found) {
            inputFile.delete();
            tempFile.renameTo(inputFile);

        } else {
            tempFile.delete();
            throw new Exception("Employee with ID " + idString + " not found.");
        }
    }

    public void addMarketingEmployee(String username, String email, String password, String address,
            int number) {
        new MarketingEmployee(username, email, password, address, number);
    }

    public void deleteMarketingEmployee(int id) throws Exception {
        String filePath = FilePaths.marketingEmployeePath;

        File inputFile = new File(filePath);
        File tempFile = new File("tempFile.txt");

        boolean found = false;
        String idString = "MarketingEmployee" + "_" + id;

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
            throw new Exception("Error deleting employee: " + e.getMessage());
        }

        if (found) {
            inputFile.delete();
            tempFile.renameTo(inputFile);
        } else {
            tempFile.delete();
            throw new Exception("Employee with ID " + idString + " not found.");
        }
    }

    public String searchMarketingEmployee(int id) throws Exception {
        String[] marketingEmployees = FileHandler.readFile(FilePaths.marketingEmployeePath);

        for (String line : marketingEmployees) {
            String[] details = line.split(",");

            if (details.length > 0 && details[0].startsWith("MarketingEmployee_")) {
                int extractedId = Integer.parseInt(details[0].substring(18));

                if (extractedId == id) {
                    return line;
                }
            }
        }

        throw new Exception("Marketing employee with ID MarketingEmployee_" + id + " not found.");
    }

    public void addInventoryEmployee(String username, String email, String password, String address,
            int number) {
        new InventoryEmployee(username, email, password, address, number);
    }

    public void deleteInventoryEmployee(int id) throws Exception {
        String filePath = FilePaths.inventoryEmployeePath;

        File inputFile = new File(filePath);
        File tempFile = new File("tempFile.txt");

        boolean found = false;
        String idString = "InventoryEmployee" + "_" + id;

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
            throw new Exception("Error deleting employee: " + e.getMessage());
        }

        if (found) {
            inputFile.delete();
            tempFile.renameTo(inputFile);
        } else {
            tempFile.delete();
            throw new Exception("Employee with ID " + idString + " not found.");
        }
    }

    public String searchInventoryEmployee(int id) throws Exception {
        String[] inventoryEmployees = FileHandler.readFile(FilePaths.inventoryEmployeePath);

        for (String line : inventoryEmployees) {
            String[] details = line.split(",");

            if (details.length > 0 && details[0].startsWith("InventoryEmployee_")) {
                int extractedId = Integer.parseInt(details[0].substring(18));

                if (extractedId == id) {
                    return line;
                }
            }
        }

        throw new Exception("Inventory employee with ID InventoryEmployee_" + id + " not found.");
    }

    public void addSellerEmployee(String username, String email, String password, String address, int number) {
        new Seller(username, email, password, address, number);
    }

    public void deleteSellerEmployee(int id) throws Exception {
        String filePath = FilePaths.sellerEmployeePath;

        File inputFile = new File(filePath);
        File tempFile = new File("tempFile.txt");

        boolean found = false;
        String idString = "Seller" + "_" + id;

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
            throw new Exception("Error deleting employee: " + e.getMessage());
        }

        if (found) {
            inputFile.delete();
            tempFile.renameTo(inputFile);
        } else {
            tempFile.delete();
            throw new Exception("Employee with ID " + idString + " not found.");
        }
    }

    public String searchSellerEmployee(int id) {
        String[] sellerEmployees = FileHandler.readFile(FilePaths.sellerEmployeePath);

        for (String line : sellerEmployees) {
            String[] details = line.split(",");

            if (details.length > 0 && details[0].startsWith("Seller_")) {
                int extractedId = Integer.parseInt(details[0].substring(7));

                if (extractedId == id) {
                    return line;
                }
            }
        }

        return "Seller employee with ID Seller_" + id + " not found.";
    }

    public String[] listAllEmployees() {

        String[] inventoryEmployees = FileHandler.readFile(FilePaths.inventoryEmployeePath);
        String[] marketingEmployees = FileHandler.readFile(FilePaths.marketingEmployeePath);
        String[] sellerEmployees = FileHandler.readFile(FilePaths.sellerEmployeePath);

        int totalSize = inventoryEmployees.length + marketingEmployees.length + sellerEmployees.length;

        String[] result = new String[totalSize];

        int currentIndex = 0;
        for (String element : inventoryEmployees) {
            result[currentIndex++] = element;
        }
        for (String element : marketingEmployees) {
            result[currentIndex++] = element;
        }
        for (String element : sellerEmployees) {
            result[currentIndex++] = element;
        }

        return result;

    }

    public String[] listAllEmployees(String employeeType) {
        String filePath;
        switch (employeeType.toLowerCase()) {
            case "marketingemployee":
                filePath = FilePaths.marketingEmployeePath;
                break;
            case "seller":
                filePath = FilePaths.sellerEmployeePath;
                break;
            case "inventoryemployee":
                filePath = FilePaths.inventoryEmployeePath;
                break;
            default:
                return new String[0];
        }

        return FileHandler.readFile(filePath);
    }

    public void updateAdmin(String newUsername, String newEmail, String newPassword, String newAddress, int newNumber)
            throws Exception {
        String filePath = FilePaths.adminPath;

        File inputFile = new File(filePath);
        File tempFile = new File("tempFile.txt");

        boolean found = false;
        String idString = "Admin_" + counter.getValue();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String[] details = currentLine.split(",");

                if (details.length > 0 && details[0].equals(idString)) {
                    found = true;
                    details[1] = newUsername;
                    details[2] = newEmail;
                    details[3] = newPassword;
                    details[4] = newAddress;
                    details[5] = newNumber + "";
                    String updatedLine = String.join(",", details);
                    writer.write(updatedLine);
                    writer.newLine();
                } else {
                    writer.write(currentLine);
                    writer.newLine();
                }
            }
        } catch (Exception e) {
            throw new Exception("Error updating admin: " + e.getMessage());
        }

        if (found) {
            inputFile.delete();
            tempFile.renameTo(inputFile);
        } else {
            tempFile.delete();
            throw new Exception("Admin with ID " + idString + " not found.");
        }
    }
}
