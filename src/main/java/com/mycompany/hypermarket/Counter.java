/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hypermarket;

/**
 *
 * @author ahmed
 */
import java.io.*;

public class Counter {

    private int value;
    private final String filePath;

    // Constructor: initializes the counter with the specified file
    public Counter(String filePath) {
        this.filePath = filePath;
        this.value = readCounterFromFile();
    }

    // Method to increment the counter and save it to the file
    public synchronized void increment() {
        value++;
        saveCounterToFile();
    }

    // Getter for the current counter value
    public synchronized int getValue() {
        return value;
    }

    // Private method to read the counter value from a file
    private int readCounterFromFile() {
        File counterFile = new File(filePath);
        if (!counterFile.exists()) {
            return 0; // Default to 0 if the file doesn't exist
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(counterFile))) {
            String value = reader.readLine();
            return (value != null) ? Integer.parseInt(value) : 0;
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading counter from file: " + e.getMessage());
            return 0; // Default to 0 if there's an error
        }
    }

    // Private method to save the counter value to a file
    private void saveCounterToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(String.valueOf(value));
        } catch (IOException e) {
            System.out.println("Error saving counter to file: " + e.getMessage());
        }
    }
}
