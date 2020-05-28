package com.example.phoneoff;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;


public class Product implements Parcelable {
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

    protected Product(Parcel in) {
        Name = in.readString();
        Cost = in.readDouble();
        Description = in.readString();
        Color = in.readString();
        Manufacturer = in.readString();
        RAM = in.readInt();
        ROM = in.readInt();
        Diagonal = in.readDouble();
        Image = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Name);
        dest.writeDouble(Cost);
        dest.writeString(Description);
        dest.writeString(Color);
        dest.writeString(Manufacturer);
        dest.writeInt(ROM);
        dest.writeInt(RAM);
        dest.writeDouble(Diagonal);
        dest.writeString(Image);
    }
}
