package com.example.phoneoff.Model;

import java.util.Date;

public class AddOrder {

    public int[] prodId;
    public String email;
    public Date date;
    public double price;

    public AddOrder(int[] prodId, String email, Date date, double price) {
        this.prodId = prodId;
        this.email = email;
        this.date = date;
        this.price = price;
    }
}
