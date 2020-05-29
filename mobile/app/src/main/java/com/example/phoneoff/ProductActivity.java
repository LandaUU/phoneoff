package com.example.phoneoff;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProductActivity extends AppCompatActivity {

    int productId;
    Product product;
    ImageView imageView;
    TabLayout tabLayout;
    Button OrderButton;
    boolean isOrdered = false;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Intent intent = getIntent();
        //Gson gs = new Gson();
        //product = gs.fromJson(intent.getStringExtra("product"), Product.class);
        productId = (int) intent.getIntExtra("productId", 0);

        db = MainActivity.db;

        ExecutorService service = Executors.newSingleThreadExecutor();

        service.execute(() -> product = db.productDao().getProduct(productId));

        service.shutdown();

        try {
            service.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        imageView = findViewById(R.id.PhoneImageA);
        tabLayout = findViewById(R.id.tabLayout);
        imageView.setImageBitmap(product.getImage());
        Description_Click();

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

        OrderButton = findViewById(R.id.AddOrderButton);
        OrderButton.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Добавлено в корзину", Toast.LENGTH_LONG).show();
            OrderButton.setBackgroundColor(Color.GREEN);
            OrderButton.setText("Добавлено");
            OrderButton.setClickable(false);
            isOrdered = true;
        });
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent resultIntent = new Intent();
        int resultcode;
        resultIntent.putExtra("OrderedProduct", product.Id);
        if (isOrdered)
            resultcode = 1;
        else
            resultcode = 2;
        setResult(resultcode, resultIntent);
        //finish();
        killActivity();
    }

    private void killActivity() {
        finish();
    }
}
