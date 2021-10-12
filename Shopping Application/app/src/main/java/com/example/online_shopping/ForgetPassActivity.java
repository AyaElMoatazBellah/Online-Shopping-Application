package com.example.online_shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ForgetPassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        final Button done = (Button)findViewById(R.id.Btn_DoneQues);
        final Button reset = (Button)findViewById(R.id.Btn_F_ResetPassword);
        final CardView C_question = (CardView) findViewById(R.id.card_question);
        final CardView  C_password = (CardView) findViewById(R.id.card_pass);
        final TextView V_Question = (TextView) findViewById(R.id.TV_Question);
        final TextView repass = (TextView) findViewById(R.id.TV_Repass_f);
        final TextView passview = (TextView) findViewById(R.id.TV_pass_f);
        final EditText answer_Q = (EditText) findViewById(R.id.Txt_answer_f);
        final EditText newpass = (EditText) findViewById(R.id.txt_Password_f);
        final EditText repass_edittxt = (EditText) findViewById(R.id.txt_RePassword_f);
        final OnlineShopping_DB App_Database = new OnlineShopping_DB(this);
        Intent i = getIntent();
        final String email = i.getStringExtra("UserName");
        V_Question.setText(App_Database.Retrive_Security_Q(email));
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(answer_Q.getText().toString()).isEmpty() && (answer_Q.getText().toString()).equals(App_Database.Retrive_Security_Q_question(email)) )
                {
                    C_question.setVisibility(View.INVISIBLE);
                    C_password.setVisibility(View.VISIBLE);
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(repass.getText().toString()).isEmpty()&& !(newpass.getText().toString()).isEmpty())
                {

                    int id = App_Database.Retrive_id(email);
                    App_Database.UpdatePassword(email,newpass.getText().toString());
                    Intent Loginactivity = new Intent(ForgetPassActivity.this , LoginActivity.class);
                    startActivity(Loginactivity);
                }
            }
        });

    }
}
