package com.example.online_shopping;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class ProductActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        final TextView proname = (TextView)findViewById(R.id.TV_ProductName);
        final TextView prodesc = (TextView)findViewById(R.id.TV_ProductDesc);
        final TextView proprice = (TextView)findViewById(R.id.TV_cartProPrice);
        final TextView pronum = (TextView)findViewById(R.id.TV_ProductNumber);
        final ImageView proimg = (ImageView)findViewById(R.id.IMV_proimg);
        final ImageButton plus = (ImageButton)findViewById(R.id.Btn_Add);
        final ImageButton minus = (ImageButton)findViewById(R.id.Btn_Subtract);
        final ImageButton shoppingcart = (ImageButton)findViewById(R.id.Btn_Cart);
        final ImageButton back = (ImageButton)findViewById(R.id.Btn_Back);
        final Button AddToCart = (Button)findViewById(R.id.Btn_AddProduct);

        final Product product = (Product) getIntent().getSerializableExtra("Product");
        proname.setText(product.getProname());
        prodesc.setText(product.getProDesc());
        proprice.setText(String.valueOf(product.getPrice())+" LE");
        proimg.setImageResource(product.getImageID());

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.valueOf(pronum.getText().toString());
                if((num+1) <= product.getQuantity())
                {
                    pronum.setText(String.valueOf(num+1));
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Sorry, but the limited quantity of this item has been exceeded ! " , Toast.LENGTH_LONG).show();
                }
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.valueOf(pronum.getText().toString());
                if((num-1) <=  0)
                {
                    pronum.setText(String.valueOf(0));
                }
                else
                {
                    pronum.setText(String.valueOf(num-1));
                }
            }
        });
        AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.valueOf(pronum.getText().toString()) != 0 )
                {
                   /* AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setMessage("The Product Has Been Added Successfully");
                    ///builder.setIcon(R.drawable.checkicon);
                    builder.setPositiveButton("Continue Shopping", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent search = new Intent(ProductActivity.this,SearchActivity.class);
                            startActivity(search);
                        }
                    }).setNegativeButton("Go To Shopping Cart", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();*/
                    Intent shoppingcart = new Intent(ProductActivity.this,ShoppingCartActivity.class);
                    shoppingcart.putExtra("Product" , product);
                    shoppingcart.putExtra("proamount" , String.valueOf(pronum.getText().toString()));
                    /*ShoppingCartActivity s  ` `;
                    s.addproduct(Integer.valueOf(pronum.getText().toString()),product);*/
                    startActivity(shoppingcart);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please choose amount" , Toast.LENGTH_LONG);
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        shoppingcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shoppingActivity = new Intent(ProductActivity.this,ShoppingCartActivity.class);
                startActivity(shoppingActivity);
            }
        });


    }
}
