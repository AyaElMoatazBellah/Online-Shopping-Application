package com.example.online_shopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    static public int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Button login = (Button)findViewById(R.id.Btn_Signin);
        final EditText UserName = (EditText) findViewById(R.id.Txt_UserName);
        final EditText Password = (EditText) findViewById(R.id.Txt_Password);
        final TextView wronginput = (TextView) findViewById(R.id.TV_Wronginput);
        final OnlineShopping_DB ShoppingDB = new OnlineShopping_DB(this);
        final TextView Forgetpass = (TextView)findViewById(R.id.TV_forgetpass);
        final CheckBox CheckBox = (CheckBox)findViewById(R.id.checkBox);

        //ShoppingDB.delete("1");
        //ShoppingDB.CreateCategories();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int CustomerID = ShoppingDB.Login_Validation(UserName.getText().toString(),Password.getText().toString());

                //  The Customer Entered Valid INFO
                if( CustomerID != 0 )
                {
                    Intent homepage = new Intent(LoginActivity.this,HomeActivity.class);
                    homepage.putExtra("id",CustomerID);
                    id = CustomerID;
                    SharedPreferences pref = getSharedPreferences("id",MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("id",CustomerID);
                    editor.apply();
                    startActivity(homepage);
                }
                else
                {
                    wronginput.setVisibility(View.VISIBLE);
                    UserName.setText("");
                    Password.setText("");
                }

            }
        });

        final Button signup = (Button)findViewById(R.id.Btn_Signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(i);
            }
        });

        Forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((UserName.getText().toString()).isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Your Email In Order TO Reset Password",Toast.LENGTH_LONG).show();
                }
                else {
                    int check = ShoppingDB.Check_email(UserName.getText().toString());
                    if(check == 1 )
                    {
                        Intent i = new Intent(LoginActivity.this,ForgetPassActivity.class);
                        i.putExtra("UserName",UserName.getText().toString());
                        startActivity(i);
                    }

                }
            }
        });
        final int CustomerID = ShoppingDB.Login_Validation(UserName.getText().toString(),Password.getText().toString());
        final Intent homepage = new Intent(LoginActivity.this,HomeActivity.class);
        SharedPreferences preff = getSharedPreferences("checkbox",MODE_PRIVATE);
        String checked = preff.getString("remember","");
        if(checked.equals("true"))
        {
            //  The Customer Entered Valid INFO
            startActivity(homepage);

        }
        CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked())
                {
                    SharedPreferences pref = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("remember","true");
                    editor.apply();
                }
                else if(!buttonView.isChecked())
                {
                    SharedPreferences pref = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("remember","false");
                    editor.apply();
                }
            }
        });


    }
}
