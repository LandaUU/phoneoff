package com.example.phoneoff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserFragment extends Fragment {

    RecyclerView recyclerView;
    TextView UserTextView;
    Auth user;
    OrderAdapter adapter;
    ArrayList<Order> orders;

    public UserFragment(Auth us) {
        user = us;
        GetOrder();
    }

    public UserFragment(ArrayList<Order> orderArrayList, Auth auth) {
        orders = orderArrayList;
        adapter = new OrderAdapter(orders);
        user = auth;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        UserTextView = view.findViewById(R.id.textViewOrder1);
        recyclerView = view.findViewById(R.id.recyclerViewOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        CharSequence sequence = UserTextView.getText();
        UserTextView.setText(sequence + user.username);
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
            OrderHolder holder = new OrderHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
            String text1 = "Заказ №";
            String text2 = "Статус:";
            try {
                if (!orderArrayList.isEmpty()) {
                    holder.textView1.setText(text1 + orderArrayList.get(position).Id);
                    holder.textView2.setText(text2 + orderArrayList.get(position).Status);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class OrderHolder extends RecyclerView.ViewHolder {
            public TextView textView1;
            public TextView textView2;


            public OrderHolder(@NonNull View itemView, TextView textView1, TextView textView2) {
                super(itemView);
                this.textView1 = textView1;
                this.textView2 = textView2;
            }

            public OrderHolder(View view) {
                super(view);
                this.textView1 = view.findViewById(R.id.textViewOrder1);
                this.textView2 = view.findViewById(R.id.textViewOrder2);
            }
        }
    }

}
