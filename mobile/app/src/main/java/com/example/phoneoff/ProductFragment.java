package com.example.phoneoff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProductFragment extends Fragment {
    RecyclerView recyclerView;
    ProductAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        recyclerView = view.findViewById(R.id.ProductRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new ProductAdapter(new String[]{"1","2","3"});
        recyclerView.setAdapter(adapter);
        return view;
    }

    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

        public String[] mArray;
        public ProductAdapter(String[] array){
            mArray = array;
        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
            ProductViewHolder productViewHolder = new ProductViewHolder(view);
            return productViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
            holder.textView.setText("Nokia 5520");
            holder.imageView.setImageResource(R.drawable.nokia);
        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class ProductViewHolder extends RecyclerView.ViewHolder{
            public TextView textView;
            public ImageView imageView;

            public ProductViewHolder(View v){
                super(v);
                imageView = v.findViewById(R.id.PhoneImage);
                textView = v.findViewById(R.id.PhoneName);
            }
        }
    }
}
