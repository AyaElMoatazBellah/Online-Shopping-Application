package com.example.online_shopping;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Serializable {
    final Context context;
    ArrayList<Product> products;
    public MyAdapter(Context context , ArrayList<Product> products ) {
        this.context = context;
        this.products=products;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder,  int position) {
        final int p2 = position;
        holder.prodimage.setImageResource(products.get(position).getImageID());
        holder.name.setText(products.get(position).getProname());
        holder.price.setText(String.valueOf(products.get(position).getPrice())+" LE");
        holder.mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ProductActivity = new Intent(context, com.example.online_shopping.ProductActivity.class);
                Product p = products.get(p2);
                ProductActivity.putExtra("Product",p);
                ProductActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(ProductActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView prodimage;
        TextView  price , name;
        ConstraintLayout mainlayout ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            prodimage = (itemView.findViewById(R.id.IMV_Proimage));
            name = (itemView.findViewById(R.id.TV_ProName));
            price = (itemView.findViewById(R.id.TV_ProPrice));
            mainlayout =(itemView.findViewById(R.id.mainLayout));
        }
    }

}
