package com.example.phoneoff;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.phoneoff.Model.ProductOrder;

import java.util.List;

@Dao
public interface ProductOrderDao {
    @Query("SELECT * FROM Product")
    List<ProductOrder> getAllProducts();

    @Query("SELECT * FROM Product WHERE Id= :Ids")
    ProductOrder getProduct(int Ids);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ProductOrder> products);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProduct(ProductOrder product);

    @Delete
    void delete(ProductOrder product);
}
