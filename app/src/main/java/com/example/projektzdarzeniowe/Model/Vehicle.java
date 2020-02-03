package com.example.projektzdarzeniowe.Model;

public class Vehicle {
    private String name, available, uid;

    public Vehicle() {
    }

    public Vehicle(String name, String available, String uid) {
        this.name = name;
        this.available = available;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public String getAvailable() {
        return available;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }


}
