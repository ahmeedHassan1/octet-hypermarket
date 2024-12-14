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

    public Counter(String filePath) {
        this.filePath = filePath;
        this.value = readCounterFromFile();
    }

    public void increment() {
        value++;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(value + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getValue() {
        return value;
    }

    private int readCounterFromFile() {
        File counterFile = new File(filePath);
        if (!counterFile.exists()) {
            return 0;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(counterFile))) {
            String value = reader.readLine();
            return (value != null) ? Integer.parseInt(value) : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
