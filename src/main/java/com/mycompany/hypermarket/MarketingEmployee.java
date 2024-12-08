/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hypermarket;

import java.io.File;

/**
 *
 * @author ahmed
 */
public class MarketingEmployee extends Person {

    private static final Counter counter = new Counter(FilePaths.marketingEmployeeCounterPath);

    public MarketingEmployee() {
//        counter++;
//        setId("MarketingEmployee_" + counter);
    }

    public MarketingEmployee(String username, String email, String password, String address, int number) {
        super(username, email, password, address, number);
        counter.increment();
        setId("MarketingEmployee_" + counter.getValue());

        File file = FileHandler.createFile(FilePaths.marketingEmployeePath);
        FileHandler.writeToFile(file, getId() + "," + username + "," + email + "," + password + "," + address + "," + number);
    }

//    public Report createReport() {
//    }
//
//    public void createOffer() {
//    }
}
