/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hypermarket;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

/**
 *
 * @author ahmed
 */
public class FileHandler {

    public static File createFile(String path) {
        File file = new File(path);

        try {
            if (file.createNewFile()) {
                System.out.println("File is Created");
            } else {
                System.out.println("File already exists");
            }

        } catch (Exception ex) {
            System.out.println("Invalid File Path");
        }
        return file;
    }

    public static void writeToFile(File file, String content, boolean append) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, append))) {
            bw.write(content);
            bw.newLine(); 
        } catch (Exception ex) {
            System.out.println("Error writing to file: " + ex.getMessage());
        }
    }

    public static String[] readFile(String filePath) {
        try {
            File file = createFile(filePath);
            Scanner fr = new Scanner(file);
            StringBuilder lines = new StringBuilder(); 
            while (fr.hasNextLine()) {
                lines.append(fr.nextLine()).append("\n");
            }
            fr.close(); 
            return lines.toString().split("\n"); 
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: " + ex.getMessage());
            return new String[0]; 
        }
    }

}
