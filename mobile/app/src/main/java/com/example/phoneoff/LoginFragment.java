package com.example.phoneoff;

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
        MainActivity mainActivity = (MainActivity) getActivity();

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!EmailText.getText().equals("") && !PassText.getText().equals("")) {
                    Auth result = DBManager.Auth(EmailText.getText().toString(), PassText.getText().toString());
                    if (result != null) {
                        mainActivity.isAuth = true;
                        UserFragment fragment = new UserFragment();
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayout, fragment);
                        fragmentTransaction.commit();
                    }
                }
            }
        });

        return view;
    }

}
