package com.example.phoneoff;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Product> orderproducts = new ArrayList<>();
    DataFragment dataFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Создание MainActivity");
        setContentView(R.layout.activity_main);
        DBManager.GetProducts(products);

        // find the retained fragment on activity restarts
        FragmentManager fm = getSupportFragmentManager();
        dataFragment = (DataFragment) fm.findFragmentByTag("data");

        // create the fragment and data the first time
        if (dataFragment == null) {
            // add the fragment
            dataFragment = new DataFragment();
            fm.beginTransaction().add(dataFragment, "data").commit();
        }


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        /*savedInstanceState.putParcelableArrayList("products", products);
        savedInstanceState.putParcelableArrayList("orderproducts", orderproducts);*/


        dataFragment.setData(products, orderproducts);
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        /*products = savedInstanceState.getParcelableArrayList("products");
        orderproducts =  savedInstanceState.getParcelableArrayList("orderproducts");*/


        products = dataFragment.getProducts();
        orderproducts = dataFragment.getOrderProducts();
    }

    public void HomeClick(MenuItem item) {
        item.setChecked(true);
        Log.i(TAG, "Нажали на home, создаем фрагмент HomeFragment");
        HomeFragment fragment = new HomeFragment();
        ChangeFragment(R.id.frameLayout, fragment);
    }

    public void ProductClick(MenuItem item) {
        item.setChecked(true);
        Log.i(TAG, "Нажали на Product, создаем фрагмент ProductFragment");
        ProductFragment fragment = new ProductFragment(products);
        ChangeFragment(R.id.frameLayout, fragment);
    }

    private void ChangeFragment(int id,Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void AccountClick(MenuItem item) {
        item.setChecked(true);
        Log.i(TAG, "Нажали на Account, создаем фрагмент AccountFragment");
        AccountFragment fragment = new AccountFragment();
        ChangeFragment(R.id.frameLayout, fragment);
    }

    public void BucketClick(MenuItem item) {
        item.setChecked(true);
        Log.i(TAG, "Нажали на Account, создаем фрагмент BucketFragment");
        BucketFragment fragment = new BucketFragment(orderproducts);
        ChangeFragment(R.id.frameLayout, fragment);
    }

    public void VKontakteClick(View view) {
        /*TODO: Сделать переадресацию на чью-нибудь страницу в вк*/

        Toast.makeText(view.getContext(), "Переадресация ВКонтакте", Toast.LENGTH_LONG).show();
    }

    public void TelegramClick(View view) {
        /*TODO: Наверно можно создать канал в телеге для переадресации*/
        Toast.makeText(view.getContext(), "Переадресация Telegram", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            orderproducts.add((Product) data.getSerializableExtra("OrderedProduct"));
        }

    }
}
