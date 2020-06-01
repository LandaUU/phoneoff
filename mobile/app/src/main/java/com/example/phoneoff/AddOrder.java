package com.example.phoneoff;

public class AddOrder {

    public int[] prodId;
    public String email;
    public String date;
    public double price;

    public AddOrder(int[] prodId, String email, String date, double price) {
        this.prodId = prodId;
        this.email = email;
        this.date = date;
        this.price = price;
    }
}
