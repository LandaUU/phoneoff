package com.example.phoneoff;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProductActivity extends AppCompatActivity {

    Product product;
    TextView textView2;
    TextView textView3;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Intent intent = getIntent();
        //Gson gs = new Gson();
        //product = gs.fromJson(intent.getStringExtra("product"), Product.class);
        product = (Product) intent.getSerializableExtra("product");
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        imageView = findViewById(R.id.PhoneImageA);
        textView2.setText(product.Name);
        textView3.setText(product.Description);
        imageView.setImageBitmap(StringToBitMap(product.Image));
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
}
