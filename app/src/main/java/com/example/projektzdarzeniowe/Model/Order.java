package com.example.projektzdarzeniowe.Model;

public class Order {
    private String date;
    private String id;
    private String user;
    private String vehicle;

    public Order(){

    }

    public Order(String date, String id, String user, String vehicle) {
        this.date = date;
        this.id = id;
        this.user = user;
        this.vehicle = vehicle;
    }

    public String getDate() {
        return date;
    }

    public String getUser() {
        return user;
    }

    public String getVehicle() {
        return vehicle;
    }

    public String getId() { return id; }

}
