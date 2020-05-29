package com.example.phoneoff;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class DataFragment extends Fragment {

    // data object we want to retain
    private ArrayList<Product> products;
    private ArrayList<Product> orderproducts;

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        //setRetainInstance(true);
    }

    public void setData(ArrayList<Product> data, ArrayList<Product> data1) {
        this.products = data;
        this.orderproducts = data1;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Product> getOrderProducts() {
        return orderproducts;
    }
}
