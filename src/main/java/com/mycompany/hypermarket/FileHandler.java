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

    public static File createFile(String filePath) {
        File file = new File(filePath);

        try {
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static void writeToFile(File file, String content, boolean append) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, append))) {
            bw.write(content);
            bw.newLine();
        } catch (Exception e) {
            e.printStackTrace();
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    public static void deleteFile(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.delete()) {
            throw new Exception("Failed to delete file.");
        }
    }

    public static boolean searchFile(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

}
