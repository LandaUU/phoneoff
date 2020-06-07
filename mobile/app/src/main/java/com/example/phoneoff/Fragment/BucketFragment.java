package com.example.phoneoff.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phoneoff.Activity.MainActivity;
import com.example.phoneoff.DBManager;
import com.example.phoneoff.Interface.AddOrderInterface;
import com.example.phoneoff.Model.AddOrder;
import com.example.phoneoff.Model.ProductOrder;
import com.example.phoneoff.R;

import java.util.ArrayList;
import java.util.Date;

public class BucketFragment extends Fragment {
    RecyclerView recyclerView;
    BucketAdapter adapter;
    private ArrayList<ProductOrder> products = new ArrayList<>();
    Button OrderButton;

    public BucketFragment(ArrayList<ProductOrder> list) {
        products = list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bucket, container, false);
        recyclerView = view.findViewById(R.id.OrdersRecycleView);
        OrderButton = view.findViewById(R.id.OrderButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new BucketAdapter(products);
        recyclerView.setAdapter(adapter);

        OrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                if (activity.isAuth) {
                    int[] ids = new int[products.size()];
                    double Summa = 0;
                    for (int i = 0; i < products.size(); i++) {
                        ids[i] = products.get(i).Id;
                        Summa += products.get(i).Cost;
                    }
                    AddOrder order = new AddOrder(ids, activity.user.username, new Date(), Summa);
                    DBManager.AddOrder(order, new AddOrderInterface() {
                        @Override
                        public void AddOrder(int orderId) {
                            Toast.makeText(getContext(), "Заказ №" + orderId + " оформлен", Toast.LENGTH_LONG).show();
                            products.clear();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Авторизуйтесь", Toast.LENGTH_LONG).show();
                }
            }
        });


        return view;
    }

    public class BucketAdapter extends RecyclerView.Adapter<BucketAdapter.BucketViewHolder> {
        ArrayList<ProductOrder> arrayList = new ArrayList<>();

        public BucketAdapter(ArrayList<ProductOrder> products) {
            arrayList = products;
        }

        public BucketAdapter() {
        }

        @NonNull
        @Override
        public BucketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bucket_item, parent, false);
            //BucketViewHolder holder = new BucketViewHolder(view, (CheckBox) view.findViewById(R.id.OrderCheckBox), (TextView) view.findViewById(R.id.OrderTextView), (ImageView) view.findViewById(R.id.OrderPhoneImage), (Button) view.findViewById(R.id.OrderDeleteButton));
            BucketViewHolder holder = new BucketViewHolder(view, view.findViewById(R.id.OrderTextView), view.findViewById(R.id.imageViewOrder), view.findViewById(R.id.CardViewOrder));
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull BucketViewHolder holder, int position) {
            holder.textView.setText(arrayList.get(position).Name);
            holder.imageView.setImageBitmap(arrayList.get(position).getBitmapImage());
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class BucketViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;
            public ImageView imageView;
            public CardView cardView;
            /*public BucketViewHolder(@NonNull View itemView, CheckBox checkBox, TextView textView, ImageView imageView, Button button) {
                super(itemView);
                this.checkBox = checkBox;
                this.textView = textView;
                this.imageView = imageView;
                this.button = button;
            }*/

            public BucketViewHolder(@NonNull View itemView, TextView textView, ImageView imageView, CardView cardView) {
                super(itemView);
                this.textView = textView;
                this.imageView = imageView;
                this.cardView = cardView;
            }
        }
    }


}
