package com.example.phoneoff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class UserFragment extends Fragment {

    RecyclerView recyclerView;
    TextView UserTextView;
    String Email;

    public UserFragment(String username) {
        Email = username;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        UserTextView = view.findViewById(R.id.textViewOrder1);

        CharSequence sequence = UserTextView.getText();
        UserTextView.setText(sequence + Email);

        return view;
    }


    public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {

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
            holder.textView1.setText(text1);
            holder.textView2.setText(text2);

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
