/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hypermarket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author ahmed
 */
public class FileHandler {

    private File file;
    private Scanner fr;
    private FileWriter fw;
    private String filePath = "";

    public FileHandler(String filePath) {
        this.filePath = filePath;
    }

    public static File createFile(String path) {
        File file = new File(path);

        try {
            if (file.createNewFile()) {
                System.out.println("File is Created");
            } else {
                System.out.println("File is already exist");
            }

        } catch (Exception ex) {
            System.out.println("Invalid File Path");
        }
        return file;
    }

    public static void writeToFile(File file, String content) {
        try {
            FileWriter fw = new FileWriter(file, true);
            fw.append(content + "\n");
            fw.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String[] readFile(String filePath) {
        try {
            File file = createFile(filePath);
            Scanner fr = new Scanner(file);
            StringBuilder lines = new StringBuilder(); // Use StringBuilder for better performance
            while (fr.hasNextLine()) {
                lines.append(fr.nextLine()).append("\n");
            }
            fr.close(); // Always close the Scanner
            return lines.toString().split("\n"); // Split the content into an array of lines
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: " + ex.getMessage());
            return new String[0]; // Return an empty array if the file is not found
        }
    }

}
