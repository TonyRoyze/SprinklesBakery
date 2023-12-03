package com.example.sprinklesbakery.Classes;

public class Category {
    private int id;
    private String catName;
    private String catDesc;

    public Category(int id, String catName, String catDesc) {
        this.id = id;
        this.catName = catName;
        this.catDesc = catDesc;
    }

    public Category() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatDesc() {
        return catDesc;
    }

    public void setCatDesc(String catDesc) {
        this.catDesc = catDesc;
    }
}
