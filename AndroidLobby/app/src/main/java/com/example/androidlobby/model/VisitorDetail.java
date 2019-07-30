package com.example.androidlobby.model;

public class VisitorDetail {

    byte[] img;
    String name;
    String role;
    String phoneNumber;
    String email;
    String company;

    public VisitorDetail() {
    }

    public VisitorDetail(byte[] img, String name, String role, String phoneNumber, String email, String company) {
        this.img = img;
        this.name = name;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.company = company;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
