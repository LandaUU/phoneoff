package com.example.phoneoff.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ProductOrder")
public class ProductOrder {
    @PrimaryKey
    public int Id;
    public String Name;
    public double Cost;
    public String Description;
    public String Color;
    public String Manufacturer;
    public int RAM;
    public int ROM;
    public double Diagonal;
    public String Image;

    public ProductOrder() {

    }

    public ProductOrder(Product product) {
        Id = product.Id;
        Name = product.Name;
        Cost = product.Cost;
        Description = product.Description;
        Color = product.Color;
        Manufacturer = product.Manufacturer;
        RAM = product.RAM;
        ROM = product.ROM;
        Diagonal = product.Diagonal;
        Image = product.Image;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public double getCost() {
        return Cost;
    }

    public String getDescription() {
        return Description;
    }

    public String getColor() {
        return Color;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public int getRAM() {
        return RAM;
    }

    public int getROM() {
        return ROM;
    }

    public double getDiagonal() {
        return Diagonal;
    }

    public String getImage() {
        return Image;
    }


    public void setId(int id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setCost(double cost) {
        Cost = cost;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setColor(String color) {
        Color = color;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    public void setRAM(int RAM) {
        this.RAM = RAM;
    }

    public void setROM(int ROM) {
        this.ROM = ROM;
    }

    public void setDiagonal(double diagonal) {
        Diagonal = diagonal;
    }

    public void setImage(String image) {
        Image = image;
    }

    public Bitmap getBitmapImage() {
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
