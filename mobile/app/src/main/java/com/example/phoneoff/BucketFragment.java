package com.example.phoneoff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BucketFragment extends Fragment {
    RecyclerView recyclerView;
    BucketAdapter adapter;
    private ArrayList<Product> products = new ArrayList<>();

    public BucketFragment(ArrayList<Product> list) {
        products = list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bucket, container, false);
        recyclerView = view.findViewById(R.id.OrdersRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new BucketAdapter(products);
        recyclerView.setAdapter(adapter);
        return view;
    }

    public class BucketAdapter extends RecyclerView.Adapter<BucketAdapter.BucketViewHolder> {
        ArrayList<Product> arrayList = new ArrayList<>();

        public BucketAdapter(ArrayList<Product> products) {
            arrayList = products;
        }

        public BucketAdapter() {
        }

        @NonNull
        @Override
        public BucketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bucket_item, parent, false);
            BucketViewHolder holder = new BucketViewHolder(view, (CheckBox) view.findViewById(R.id.OrderCheckBox), (TextView) view.findViewById(R.id.OrderTextView), (ImageView) view.findViewById(R.id.OrderPhoneImage), (Button) view.findViewById(R.id.OrderDeleteButton));
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull BucketViewHolder holder, int position) {
            holder.textView.setText(arrayList.get(position).Name);
            holder.imageView.setImageBitmap(arrayList.get(position).getImage());
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class BucketViewHolder extends RecyclerView.ViewHolder {
            public CheckBox checkBox;
            public TextView textView;
            public ImageView imageView;
            public Button button;

            public BucketViewHolder(@NonNull View itemView, CheckBox checkBox, TextView textView, ImageView imageView, Button button) {
                super(itemView);
                this.checkBox = checkBox;
                this.textView = textView;
                this.imageView = imageView;
                this.button = button;
            }
        }
    }


}
