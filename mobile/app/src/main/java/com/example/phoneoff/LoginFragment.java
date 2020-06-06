package com.example.phoneoff;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class LoginFragment extends Fragment {

    Button LoginButton;
    Button RegistrationButton;
    EditText EmailText;
    EditText PassText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);

        LoginButton = view.findViewById(R.id.ButtonLogin);
        RegistrationButton = view.findViewById(R.id.ButtonRegistration);
        EmailText = view.findViewById(R.id.editTextTextEmailAddress);
        PassText = view.findViewById(R.id.editTextTextPassword);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!EmailText.getText().equals("") && !PassText.getText().equals("")) {
                    DBManager.Auth(EmailText.getText().toString(), PassText.getText().toString(), new LoginInterface() {
                        @Override
                        public void Login(Auth result) {
                            if (result.access_token != null) {
                                GetOrder(result);
                            }
                        }
                    });

                }
            }
        });

        RegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RegistrationActivity.class);
                startActivityForResult(intent, 3);
            }
        });

        return view;
    }

    private void GetOrder(Auth result) {
        DBManager.GetOrder(result.access_token, new GetOrderInterface() {
            @Override
            public void GetOrder(ArrayList<Order> orders) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.isAuth = true;
                mainActivity.user = result;
                UserFragment fragment;
                //fragment = new UserFragment(result);
                fragment = new UserFragment(orders, result);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.commit();
            }
        });
    }
}
