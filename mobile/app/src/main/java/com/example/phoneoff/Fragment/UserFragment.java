package com.example.phoneoff.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phoneoff.Activity.MainActivity;
import com.example.phoneoff.DBManager;
import com.example.phoneoff.Interface.GetOrderInterface;
import com.example.phoneoff.Model.Auth;
import com.example.phoneoff.Model.Order;
import com.example.phoneoff.R;

import java.util.ArrayList;

public class UserFragment extends Fragment {

    RecyclerView recyclerView;
    TextView UserTextView;
    Auth user;
    OrderAdapter adapter;
    ArrayList<Order> orders;
    Button UnloginButton;

    public UserFragment(Auth us) {
        user = us;
        GetOrder();
    }

    public UserFragment(ArrayList<Order> orderArrayList, Auth auth) {
        orders = orderArrayList;
        user = auth;
        adapter = new OrderAdapter(orders);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        UserTextView = view.findViewById(R.id.textViewOrder1);
        UnloginButton = view.findViewById(R.id.UnloginButton);
        recyclerView = view.findViewById(R.id.recyclerViewOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        //adapter = new OrderAdapter(orders);
        recyclerView.setAdapter(adapter);
        CharSequence sequence = UserTextView.getText();
        UserTextView.setText(sequence + user.username);

        UnloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.isAuth = false;
                mainActivity.user = null;
                LoginFragment fragment = new LoginFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.commit();
            }
        });


        return view;
    }

    private void GetOrder() {
        DBManager.GetOrder(user.access_token, new GetOrderInterface() {
            @Override
            public void GetOrder(ArrayList<Order> orders) {
                adapter = new OrderAdapter(orders);
                recyclerView.setAdapter(adapter);
            }
        });
    }


    public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {
        ArrayList<Order> orderArrayList;

        public OrderAdapter(ArrayList<Order> orderArrayList) {
            this.orderArrayList = orderArrayList;
        }

        public OrderAdapter() {
        }

        @NonNull
        @Override
        public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);

            return new OrderHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
            String text1 = "Заказ №";
            String text2 = "Статус:";
            String text3 = "Сумма:";
            try {
                if (!orderArrayList.isEmpty()) {
                    holder.textView1.setText(text1 + orderArrayList.get(position).idOrder);
                    holder.textView2.setText(text2 + orderArrayList.get(position).status);
                    holder.textView3.setText(text3 + orderArrayList.get(position).summa);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return orderArrayList.size();
        }

        public class OrderHolder extends RecyclerView.ViewHolder {
            public TextView textView1;
            public TextView textView2;
            public TextView textView3;

            public OrderHolder(View view) {
                super(view);
                this.textView1 = view.findViewById(R.id.textView15);
                this.textView2 = view.findViewById(R.id.textView16);
                this.textView3 = view.findViewById(R.id.textView23);
            }
        }
    }

}
