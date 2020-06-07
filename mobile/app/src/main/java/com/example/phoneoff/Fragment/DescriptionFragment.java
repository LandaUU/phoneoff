package com.example.phoneoff.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.phoneoff.Model.Product;
import com.example.phoneoff.R;

public class DescriptionFragment extends Fragment {

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;
    TextView textView8;
    TextView textView9;
    Product product;

    public DescriptionFragment(Product product1) {
        product = product1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_description, container, false);
        textView1 = view.findViewById(R.id.NameDescriptionTextView);
        textView2 = view.findViewById(R.id.DescriptionTextView);
        textView3 = view.findViewById(R.id.OsnCharTextView);
        textView4 = view.findViewById(R.id.textView7);
        textView5 = view.findViewById(R.id.textView11);
        textView6 = view.findViewById(R.id.textView12);
        textView7 = view.findViewById(R.id.textView13);
        textView8 = view.findViewById(R.id.textView14);
        textView9 = view.findViewById(R.id.textView22);
        textView1.setText(product.Name);
        textView2.setText(product.Description);
        textView4.setText(textView4.getText() + product.Manufacturer);
        textView5.setText(textView5.getText() + product.Color);
        textView6.setText(textView6.getText() + String.valueOf(product.RAM));
        textView7.setText(textView7.getText() + String.valueOf(product.ROM));
        textView8.setText(textView8.getText() + String.valueOf(product.Diagonal));
        textView9.setText(textView9.getText() + String.valueOf(product.Count));
        return view;
    }
}
