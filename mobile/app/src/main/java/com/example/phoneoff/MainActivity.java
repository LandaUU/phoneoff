package com.example.phoneoff;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Создание MainActivity");
        setContentView(R.layout.activity_main);
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
        ProductFragment fragment = new ProductFragment();
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
        BucketFragment fragment = new BucketFragment();
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
}
