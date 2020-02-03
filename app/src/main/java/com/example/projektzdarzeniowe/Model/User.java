package com.example.projektzdarzeniowe.Model;

public class User {
    private String admin;
    private String name;
    private String uid;
    private String email;
    private String phone;

    public User(){

    }

    public User(String email, String name, String uid, String phone, String admin) {
        this.admin = admin;
        this.name = name;
        this.uid = uid;
        this.email = email;
        this.phone = phone;
    }

    public String getAdmin() {
        return admin;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
