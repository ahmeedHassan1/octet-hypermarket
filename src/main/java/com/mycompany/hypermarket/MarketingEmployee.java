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
public class MarketingEmployee extends Person {

    private static final Counter counter = new Counter(FilePaths.marketingEmployeeCounterPath);

    public MarketingEmployee() {
    }

    public MarketingEmployee(String username, String email, String password, String address, int number) {
        super(username, email, password, address, number);
        counter.increment();
        setId("MarketingEmployee_" + counter.getValue());

        File file = FileHandler.createFile(FilePaths.marketingEmployeePath);
        FileHandler.writeToFile(file,
                getId() + "," + username + "," + email + "," + password + "," + address + "," + number, true);
    }

    public String[] login(String email, String password) throws Exception {
        String[] employees = FileHandler.readFile(FilePaths.marketingEmployeePath);

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

    public void createReport(String content) {
        new Report(getId(), content);
    }

    public void createOffer(int idToUpdate, double discount) throws Exception {
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
                    details[5] = discount + "";
                    String updatedLine = String.join(",", details);

                    writer.write(updatedLine);
                    writer.newLine();
                } else {
                    writer.write(currentLine);
                    writer.newLine();
                }
            }
        } catch (Exception e) {
            throw new Exception("Error updating discount: " + e.getMessage());
        }

        if (found) {
            inputFile.delete();
            tempFile.renameTo(inputFile);

        } else {
            tempFile.delete();
            throw new Exception("Product with ID " + idString + " not found.");
        }
    }

    public void updateMarketingEmployee(String username, String email, String password,
            String address, int number) throws Exception {
        String filePath = FilePaths.marketingEmployeePath;

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
