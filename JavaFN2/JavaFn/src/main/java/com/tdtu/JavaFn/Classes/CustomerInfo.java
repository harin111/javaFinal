package com.tdtu.JavaFn.Classes;

public class CustomerInfo {

    private String phoneNumber;
    private String fullName;
    private String address;

    public CustomerInfo() {
    }

    public CustomerInfo(String phoneNumber, String fullName, String address) {
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
