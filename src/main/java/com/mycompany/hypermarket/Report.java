/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hypermarket;

import java.io.File;
import java.util.Date;

/**
 *
 * @author ahmed
 */
public class Report {
    private static final Counter counter = new Counter(FilePaths.reportCounterPath);
    private String id;
    private Date createdAt;
    private String owner;
    private String content;

    public Report() {
    }

    public Report(String owner, String content) {

        this.createdAt = new Date();
        this.owner = owner;
        this.content = content;
        counter.increment();
        this.setId("Report_" + counter.getValue());

        File file = FileHandler.createFile(FilePaths.reportPath);
        FileHandler.writeToFile(file,
                getId() + "," + owner + "," + content + "," + createdAt, true);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
