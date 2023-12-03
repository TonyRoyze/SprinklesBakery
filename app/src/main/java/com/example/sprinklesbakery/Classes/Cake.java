package com.example.sprinklesbakery.Classes;

public class Cake {

    private int id;
    private String cakeName;
    private String cakeDesc;
    private String imgName;
    private int catId;
    private int quantity;
    private double price;

    public Cake(int id, String cakeName, String cakeDesc, String imgName,  double price, int quantity, int catId) {
        this.id = id;
        this.cakeName = cakeName;
        this.cakeDesc = cakeDesc;
        this.imgName = imgName;
        this.quantity = quantity;
        this.catId = catId;
        this.price = price;
    }

    public Cake() {
    }

    public String getCakeName() {
        return cakeName;
    }

    public void setCakeName(String cakeName) {
        this.cakeName = cakeName;
    }

    public String getCakeDesc() {
        return cakeDesc;
    }

    public void setCakeDesc(String cakeDesc) {
        this.cakeDesc = cakeDesc;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
