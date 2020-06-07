package com.example.phoneoff;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.phoneoff.Model.Product;
import com.example.phoneoff.Model.ProductOrder;

@Database(entities = {Product.class, ProductOrder.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
}
