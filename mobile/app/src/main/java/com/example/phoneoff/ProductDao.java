package com.example.phoneoff;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM Product")
    LiveData<List<Product>> getAllProducts();

    @Query("SELECT * FROM Product WHERE Id= :Ids")
    Product getProduct(int Ids);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllProducts(List<Product> products);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProduct(Product product);

    @Delete
    void deleteProduct(Product product);

    @Query("SELECT * FROM ProductOrder")
    LiveData<List<ProductOrder>> getAllProductsOrder();

    @Query("SELECT * FROM ProductOrder WHERE Id= :Ids")
    ProductOrder getProductOrder(int Ids);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllProductsOrder(List<ProductOrder> products);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProductOrder(ProductOrder product);

    @Delete
    void deleteProductOrder(ProductOrder product);

}
