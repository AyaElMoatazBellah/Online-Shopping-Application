package com.example.online_shopping;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Button Buy=(Button)findViewById(R.id.Btn_C_Buy);
        TextView cost = (TextView) findViewById(R.id.TV_COst);
        final EditText Email = (EditText) findViewById(R.id.txt_C_Email);
        TextView Location = (TextView) findViewById(R.id.TV_Location);
        cost.setText(ShoppingCartActivity.totalPrice.getText().toString());
        final OnlineShopping_DB App_Database = new OnlineShopping_DB(this);

        SharedPreferences prefs = getSharedPreferences("id", MODE_PRIVATE);
        int idName = prefs.getInt("id", 0); //0 is the default value.

        Email.setText(App_Database.Retrive_Email(idName));
        Location.setText(LocationActivity.address);
        final String textMessage =" You Submited The Order";
        final String Password = App_Database.Retrive_Password(idName);

        Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(Email.getText().toString()).equals(""))
                {
                    final Dialog dialog = new Dialog(CheckoutActivity.this);
                    dialog.setContentView(R.layout.customdialog);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.setCancelable(false);

                    Button positive = dialog.findViewById(R.id.Btn_positive);
                    Button negative = dialog.findViewById(R.id.Btn_negative);
                    final TextView message = dialog.findViewById(R.id.TV_message);
                    message.setText("Do You Want to send confirmation mail to your Email ?");
                    positive.setText("Yes");
                    negative.setText("No");
                    positive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(),"Done" , Toast.LENGTH_LONG).show();
                            Properties prop = new Properties();
                            prop.put("mail.smtp.auth","true");
                            prop.put("mail.smtp.starttls.enable","true");
                            prop.put("mail.smtp.host","smtp.gmail.com");
                            prop.put("mail.smtp.port","587");
                            Session session = Session.getInstance(prop, new Authenticator() {
                                @Override
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    Toast.makeText(getApplicationContext(),Email.getText().toString() +"  "+Password,Toast.LENGTH_LONG).show();
                                    return new PasswordAuthentication(Email.getText().toString() ,Password);
                                }
                            });
                            try {
                                Message mes = new MimeMessage(session);
                                try {
                                    ((MimeMessage) mes).setFrom(new InternetAddress(Email.getText().toString()));
                                } catch (MessagingException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    String em = Email.getText().toString();
                                    mes.addRecipient(Message.RecipientType.TO, new InternetAddress(em));
                                } catch (MessagingException e) {
                                    e.printStackTrace();
                                }
                                mes.setSubject("Sending Confirmation Order Email");
                                ((MimeMessage) mes).setText(textMessage);
                                Transport.send(mes);
                            }
                            catch (MessagingException e)
                            {
                                throw new RuntimeException(e);
                            }
                            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                            Date date = new Date(System.currentTimeMillis());
                            App_Database.CreateNewOrder( formatter.format(date));
                               // App_Database.CreateNewOrderDetails(App_Database.Retrive_orderid(formatter.format(date)),ShoppingCartActivity.ProductsList);


                        }
                    });
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    negative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });
    }
}
