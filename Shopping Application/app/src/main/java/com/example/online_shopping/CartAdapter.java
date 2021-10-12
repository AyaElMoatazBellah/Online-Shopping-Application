package com.example.online_shopping;

import android.app.AppComponentFactory;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>  {
    final Context context;
    ArrayList<Product> products;
    ArrayList<Integer> amount;
    public CartAdapter(Context context ,   ArrayList<Product> products ,ArrayList<Integer> amount) {
        this.context = context;
        this.products=products;
        this.amount=amount;
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_row, parent, false);
        return new MyViewHolder(view);
    }

    public ArrayList<Integer> l =new ArrayList<Integer>();
    @Override
    public void onBindViewHolder(@NonNull final CartAdapter.MyViewHolder holder, final int position) {
        final int position2 = position;
        if(! l.contains(position2))
        {
            l.add(position2);
            // intialize the row parameters
            holder.prodimage.setImageResource(products.get(position).getImageID());
            holder.name.setText(products.get(position).getProname());
            holder.proanmount.setText(String.valueOf(amount.get(position)));
            int price = (products.get(position).getPrice())*(amount.get(position));
            holder.price.setText(String.valueOf(price)+" LE");
            // in order to fill the total price for shopping cart
            String total = ShoppingCartActivity.totalPrice.getText().toString();
            int val =  Integer.valueOf(total.substring(0,total.length()- 3));
            final int totalprice = val + price;
            ShoppingCartActivity.totalPrice.setText(String.valueOf(totalprice + " LE"));
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pr = holder.price.getText().toString();
                int price = Integer.valueOf((pr).substring(0,pr.length()- 3));
                String total = ShoppingCartActivity.totalPrice.getText().toString();
                int totalprice = Integer.valueOf(total.substring(0,total.length()- 3)) ;
                int t = totalprice - price;
                ShoppingCartActivity.totalPrice.setText(String.valueOf(t)+ " LE");
                products.remove(position);
                amount.remove(position);
                ShoppingCartActivity.myAdapter.notifyDataSetChanged();
            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.valueOf(holder.proanmount.getText().toString());
                if((num+1) <= products.get(position2).getQuantity())
                {
                    holder.proanmount.setText(String.valueOf(num+1));
                    int inc = amount.get(position2)+ 1;
                    amount.add(position2,inc);
                    String pr = holder.price.getText().toString();
                    int price = Integer.valueOf((pr).substring(0,pr.length()- 3));
                    price += products.get(position2).getPrice();
                    holder.price.setText(String.valueOf(price)+" LE");
                    String total = ShoppingCartActivity.totalPrice.getText().toString();
                    int totalprice = Integer.valueOf(total.substring(0,total.length()- 3)) + (products.get(position2).getPrice());
                    ShoppingCartActivity.totalPrice.setText(String.valueOf(totalprice + " LE"));

                }
                else {
                    Toast.makeText(context,"Sorry, but the limited quantity of this item has been exceeded ! " , Toast.LENGTH_LONG).show();
                }
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.valueOf(holder.proanmount.getText().toString());
                if((num-1) <  1)
                {
                    Toast.makeText(context,"Sorry, this the least limit " , Toast.LENGTH_LONG).show();
                }
                else
                {
                    holder.proanmount.setText(String.valueOf(num-1));
                    int inc = amount.get(position2)- 1;
                    amount.add(position2,inc);
                    String pr = holder.price.getText().toString();
                    int price = Integer.valueOf((pr).substring(0,pr.length()- 3));
                    price -= products.get(position2).getPrice();
                    holder.price.setText(String.valueOf(price) +" LE");
                    String total = ShoppingCartActivity.totalPrice.getText().toString();
                    int totalprice = Integer.valueOf(total.substring(0,total.length()- 3)) - (products.get(position2).getPrice());
                    ShoppingCartActivity.totalPrice.setText(String.valueOf(totalprice + " LE"));
                }
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
        TextView price , name , proanmount;
        ConstraintLayout cartlayout ;
        ImageButton plus , minus ;
        Button delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            prodimage = (itemView.findViewById(R.id.IMV_cartProimage));
            name = (itemView.findViewById(R.id.TV_cartProName));
            price = (itemView.findViewById(R.id.TV_cartProPrice));
            proanmount = (itemView.findViewById(R.id.TV_cartProductNumber));
            plus = (itemView.findViewById(R.id.Btn_cartAdd));
            minus = (itemView.findViewById(R.id.Btn_cartSubtract));
            delete = (itemView.findViewById(R.id.Btn_Delete));
            cartlayout =(itemView.findViewById(R.id.cartlayout));
        }
    }
}
