package com.example.sprinklesbakery.Classes;

public class Order {
    private int id;
    private int userId;
    private String address;
    private String items;
    private double total;

    public Order(int id, int userId, String address, String items, double total) {
        this.id = id;
        this.userId = userId;
        this.address = address;
        this.items = items;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
     return userId;
    }

    public void setUserId(int userId) {
     this.userId = userId;
    }

    public String getAddress() {
     return address;
    }

    public void setAddress(String address) {
     this.address = address;
    }

    public String getItems() {
     return items;
    }

    public void setItems(String items) {
     this.items = items;
    }

    public double getTotal() {
     return total;
    }

    public void setTotal(double total) {
     this.total = total;
    }
}