package com.example.phoneoff;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.Serializable;

public class Product implements Serializable {
    public String Name;
    public double Cost;
    public String Description;
    public String Color;
    public String Manufacturer;
    public int RAM;
    public int ROM;
    public double Diagonal;
    public String Image;

    public Product(String name, double cost, String description, String color, String manufacturer, int RAM, int ROM, double diagonal, String image) {
        Name = name;
        Cost = cost;
        Description = description;
        Color = color;
        Manufacturer = manufacturer;
        this.RAM = RAM;
        this.ROM = ROM;
        Diagonal = diagonal;
        this.Image = image;
    }

    public Bitmap getImage() {
        return StringToBitMap(Image);
    }


    private Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
