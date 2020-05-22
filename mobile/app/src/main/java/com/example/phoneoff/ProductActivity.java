package com.example.phoneoff;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

public class ProductActivity extends AppCompatActivity {

    Product product;
    ImageView imageView;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Intent intent = getIntent();
        //Gson gs = new Gson();
        //product = gs.fromJson(intent.getStringExtra("product"), Product.class);
        product = (Product) intent.getSerializableExtra("product");
        imageView = findViewById(R.id.PhoneImageA);
        tabLayout = findViewById(R.id.tabLayout);
        imageView.setImageBitmap(StringToBitMap(product.Image));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().toString().contains("Описание")) {
                    Description_Click();
                } else {
                    Characteristic_Click();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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

    public void Description_Click() {
        DescriptionFragment fragment = new DescriptionFragment(product);
        ChangeFragment(R.id.PhoneLayout, fragment);
    }

    public void Characteristic_Click() {
        CharacteristicFragment fragment = new CharacteristicFragment(product);
        ChangeFragment(R.id.PhoneLayout, fragment);
    }


    private void ChangeFragment(int id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
