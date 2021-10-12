package com.example.online_shopping;

import java.io.Serializable;

public class Product implements Serializable {

    private int id ;
    private int imageID ;
    private String Proname ;
    private String ProDesc;
    private int Price;
    private int Quantity ;

    public void setId(int id) {
        this.id = id;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public void setProname(String proname) {
        Proname = proname;
    }

    public void setProDesc(String proDesc) {
        ProDesc = proDesc;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public void setCatID(int catID) {
        CatID = catID;
    }

    private int CatID ;

    public int getId() {
        return id;
    }

    public int getImageID() {
        return imageID;
    }

    public String getProname() {
        return Proname;
    }

    public String getProDesc() {
        return ProDesc;
    }

    public int getPrice() {
        return Price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public int getCatID() {
        return CatID;
    }



}
