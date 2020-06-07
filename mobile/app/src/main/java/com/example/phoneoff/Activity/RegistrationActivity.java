package com.example.phoneoff.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phoneoff.DBManager;
import com.example.phoneoff.Interface.RegistrationInterface;
import com.example.phoneoff.Model.RegistrationUser;
import com.example.phoneoff.R;

public class RegistrationActivity extends AppCompatActivity {
    EditText EmailText;
    EditText LoginText;
    EditText PassText;
    EditText Pass1Text;
    EditText FIOText;
    EditText AddressText;
    EditText PhoneText;
    Button RegButton;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        EmailText = findViewById(R.id.EmailText);
        LoginText = findViewById(R.id.LoginText);
        PassText = findViewById(R.id.TextPass);
        Pass1Text = findViewById(R.id.TextPassConfirm);
        FIOText = findViewById(R.id.TextFIO);
        AddressText = findViewById(R.id.TextAddress);
        PhoneText = findViewById(R.id.TextPhone);
        RegButton = findViewById(R.id.RegButton);

        RegButton.setOnClickListener(v -> {
            if (EmailText.getText().toString().isEmpty() || LoginText.getText().toString().isEmpty() || PassText.getText().toString().isEmpty()
                    || Pass1Text.getText().toString().isEmpty() || FIOText.getText().toString().isEmpty() || AddressText.getText().toString().isEmpty()
                    || PhoneText.getText().toString().isEmpty() || RegButton.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Заполните поля", Toast.LENGTH_LONG).show();
            } else {
                if (!EmailText.getText().toString().trim().matches(emailPattern)) {
                    Toast.makeText(getApplicationContext(), "Неправильный формат Email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!PassText.getText().toString().equals(Pass1Text.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Пароль не совпадает", Toast.LENGTH_LONG).show();
                    return;
                }

                DBManager.Registration(new RegistrationUser(EmailText.getText().toString(), LoginText.getText().toString(),
                        PassText.getText().toString(), FIOText.getText().toString(), AddressText.getText().toString(),
                        PhoneText.getText().toString()), new RegistrationInterface() {
                    @Override
                    public void Registration(boolean result) {
                        if (result) {
                            Toast.makeText(getApplicationContext(), "Вы успешно зарегистрировались", Toast.LENGTH_LONG).show();
                            setResult(3);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Не удалось зарегистрироваться", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                /*if (!DBManager.Registration(new RegistrationUser(EmailText.getText().toString(), LoginText.getText().toString(),
                        PassText.getText().toString(), FIOText.getText().toString(), AddressText.getText().toString(),
                        PhoneText.getText().toString()))) {
                    Toast.makeText(getApplicationContext(), "Не удалось зарегистрироваться", Toast.LENGTH_LONG).show();
                } else {
                    setResult(3);
                    Toast.makeText(getApplicationContext(), "Вы успешно зарегистрировались", Toast.LENGTH_LONG).show();
                    finish();
                }*/
            }
        });
    }
}
