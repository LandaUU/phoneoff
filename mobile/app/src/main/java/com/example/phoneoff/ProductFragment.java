package com.example.phoneoff;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductFragment extends Fragment {
    RecyclerView recyclerView;
    ProductAdapter adapter;
    ArrayList<Product> arrayList = new ArrayList<>();


    public ProductFragment(ArrayList<Product> products) {
        arrayList = products;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product, container, false);

        recyclerView = view.findViewById(R.id.ProductRecycleView);

        //recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 4));

        adapter = new ProductAdapter(arrayList);

        recyclerView.setAdapter(adapter);

        return view;
    }

    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

        public ArrayList<Product> productArrayList = new ArrayList<Product>();

        public ProductAdapter(ArrayList<Product> array) {
            productArrayList = array;
        }

        public ProductAdapter() {
        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView index = v.findViewById(R.id.Index);
                    Intent intent = new Intent(v.getContext(), ProductActivity.class);
                    try {
                        intent.putExtra("productId", arrayList.get(Integer.parseInt(index.getText().toString())).Id);
                        startActivityForResult(intent, 1);
                    } catch (Exception ex) {
                        Log.e("ProductFragment", ex.getMessage());
                    }
                }
            });
            ProductViewHolder productViewHolder = new ProductViewHolder(view);
            return productViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
            holder.textView.setText(productArrayList.get(position).Name);
            String ind = String.valueOf(position);
            Bitmap imagePhone;
            try {
                imagePhone = productArrayList.get(position).getImage();
                holder.index.setText(ind);
                holder.imageView.setImageBitmap(imagePhone);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            /*TODO: Сделать отображение данных на ProductRecyclerView */
        }

        @Override
        public int getItemCount() {
            return productArrayList.size();
        }

        public class ProductViewHolder extends RecyclerView.ViewHolder{
            public TextView textView;
            public TextView index;
            public ImageView imageView;
            public ProductViewHolder(View v){
                super(v);
                imageView = v.findViewById(R.id.PhoneImage);
                textView = v.findViewById(R.id.PhoneName);
                index = v.findViewById(R.id.Index);
            }
        }
    }
}
