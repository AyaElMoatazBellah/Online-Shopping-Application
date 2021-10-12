package com.example.online_shopping;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final BottomNavigationView BT_Nav = (BottomNavigationView)findViewById(R.id.bottombar);
        BT_Nav.setOnNavigationItemSelectedListener(navlistener);


    }
    private BottomNavigationView.OnNavigationItemSelectedListener navlistener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null ;
                    selectedFragment = new fr_home();
                    switch(item.getItemId())
                    {
                        case(R.id.Btn_MyAccount):
                            selectedFragment = new fr_account();
                            break;
                        case(R.id.Btn_Home):
                            selectedFragment = new fr_home();
                            break;
                        case(R.id.Btn_Orders):
                            selectedFragment = new fr_order();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.FR_Contianer,selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        { final Dialog dialog = new Dialog(HomeActivity.this);
            dialog.setContentView(R.layout.customdialog);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);

            Button positive = dialog.findViewById(R.id.Btn_positive);
            Button negative = dialog.findViewById(R.id.Btn_negative);
            TextView message = dialog.findViewById(R.id.TV_message);
            message.setText("Are You Sure You Want Exit ?");
            positive.setText("Exit");
            negative.setText("Cancel");
            positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    SharedPreferences pref = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("remember","false");
                    editor.apply();
                }
            });
            negative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
