package com.example.phoneoff.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.phoneoff.Model.Product;
import com.example.phoneoff.R;

public class CharacteristicFragment extends Fragment {
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    Product product;

    public CharacteristicFragment(Product value) {
        product = value;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_characteristic, container, false);
        textView1 = view.findViewById(R.id.textView2);
        textView2 = view.findViewById(R.id.textView3);
        textView3 = view.findViewById(R.id.textView8);
        textView4 = view.findViewById(R.id.textView9);
        textView5 = view.findViewById(R.id.textView10);
        textView6 = view.findViewById(R.id.textview16);

        textView2.setText(textView2.getText() + product.Manufacturer);
        textView3.setText(textView3.getText() + product.Color);
        textView4.setText(textView4.getText() + String.valueOf(product.RAM));
        textView5.setText(textView5.getText() + String.valueOf(product.ROM));
        textView6.setText(textView6.getText() + String.valueOf(product.Diagonal));
        return view;
    }

}
