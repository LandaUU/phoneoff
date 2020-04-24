package com.example.phoneoff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class HomeFragment extends Fragment {
    Button TestButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TestButton = view.findViewById(R.id.TestButton);

        TestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String url = "https://192.168.1.13:5001/Phone/GetPhones";
                    final String[] result = new String[1];
                    RequestQueue queue = Volley.newRequestQueue(v.getContext());
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            result[0] = response;
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            result[0] = error.getMessage();
                        }
                    });
                    queue.add(request);
                    if (result[0].length() > 0)
                        Toast.makeText(v.getContext(), "Текст был доставлен!", Toast.LENGTH_LONG);
                    else
                        Toast.makeText(v.getContext(), "Текст не был доставлен!", Toast.LENGTH_LONG);
                } catch (Exception ex) {
                    Toast.makeText(v.getContext(), ex.getMessage(), Toast.LENGTH_LONG);
                }
            }
        });
        return view;
    }


}

