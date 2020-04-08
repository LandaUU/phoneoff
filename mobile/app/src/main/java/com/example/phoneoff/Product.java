package com.example.phoneoff;

public class Product {
    public String Name;
    public double Cost;
    public String Description;
    public String Color;
    public String Manufacturer;
    public int RAM;
    public int ROM;
    public double Diagonal;
    public Byte[] image;

    public Product(String name, double cost, String description, String color, String manufacturer, int RAM, int ROM, double diagonal, Byte[] image) {
        Name = name;
        Cost = cost;
        Description = description;
        Color = color;
        Manufacturer = manufacturer;
        this.RAM = RAM;
        this.ROM = ROM;
        Diagonal = diagonal;
        this.image = image;
    }
}
