package com.example.online_shopping;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ShoppingCartActivity extends AppCompatActivity {
     static ArrayList<Product> ProductsList = new ArrayList<>();
     static ArrayList<Integer> ProductsAmount = new ArrayList<>();
     static RecyclerView cartRV;
     static CartAdapter myAdapter;
     static TextView totalPrice ;
    public void addproduct (int proamount,Product p )
    {
        ProductsList.add(p);
        ProductsAmount.add(proamount);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        final Button deleteall = (Button)findViewById(R.id.Btn_deleteall);
        final Button buy = (Button)findViewById(R.id.btn_CartAddProduct);
        final ImageButton back = (ImageButton)findViewById(R.id.Btn_cartback);
        totalPrice = (TextView)findViewById(R.id.TV_totalprice);
        cartRV = (RecyclerView)findViewById(R.id.RV_shoppingcart);

        if(getIntent().hasExtra("proamount") && getIntent().hasExtra("Product"))
        {
            final Product product = (Product) getIntent().getSerializableExtra("Product");
            ProductsList.add(product);
            int amount  = Integer.valueOf(getIntent().getStringExtra("proamount"));
            ProductsAmount.add(amount);
        }
        myAdapter = new CartAdapter(getApplicationContext(),ProductsList ,ProductsAmount);
        cartRV.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        cartRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        deleteall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ShoppingCartActivity.this);
                dialog.setContentView(R.layout.customdialog);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);

                Button positive = dialog.findViewById(R.id.Btn_positive);
                Button negative = dialog.findViewById(R.id.Btn_negative);
                TextView message = dialog.findViewById(R.id.TV_message);
                message.setText("Are You Sure You Want To Delete All Products");
                positive.setText("Delete All");
                negative.setText("Cancel");
                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProductsList.clear();
                        ProductsAmount.clear();
                        CartAdapter myAdapter = new CartAdapter(getApplicationContext(),ProductsList ,ProductsAmount);
                        cartRV.setAdapter(myAdapter);
                        myAdapter.notifyDataSetChanged();
                        cartRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        dialog.dismiss();
                    }
                });
                negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                totalPrice.setText("0 LE");
            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myAdapter.getItemCount() != 0 )
                {
                    Intent i = new Intent(ShoppingCartActivity.this,LocationActivity.class);
                    startActivity(i);
                }

            }
        });

    }
}
