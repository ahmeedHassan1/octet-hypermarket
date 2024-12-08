/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hypermarket;

import java.util.Date;

/**
 *
 * @author ahmed
 */
public class Report {

    private String id;
    private static int counter;
    private Date createdAt;
    private Person owner;
    private String content;

    public Report(Date createdAt, Person owner, String content) {
        this.createdAt = new Date();
        this.owner = owner;
        this.content = content;
        counter++;
        this.setId("Report_" + counter);
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

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
