package com.example.online_shopping;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG ="SinUpActivity";
    DatePickerDialog.OnDateSetListener DatesetOnDate ;
    OnlineShopping_DB ShoppingDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final TextView DateView = (TextView)findViewById(R.id.TxtV_Date);
        final EditText txt_name = (EditText)findViewById(R.id.txt_UserName);
        final EditText txt_email = (EditText)findViewById(R.id.txt_Email);
        final EditText txt_pass = (EditText)findViewById(R.id.txt_Password);
        final EditText txt_repass = (EditText)findViewById(R.id.txt_RePassword);
        final EditText txt_phone = (EditText)findViewById(R.id.txt_Phone);
        final EditText txt_Qanswer = (EditText)findViewById(R.id.txt_SecurityQ);
        final EditText txt_job = (EditText)findViewById(R.id.txt_Job);
        final RadioButton C = (RadioButton)findViewById(R.id.Btn_Female);
        final RadioButton F = (RadioButton)findViewById(R.id.Btn_Male);
        final Spinner SQ = (Spinner)findViewById(R.id.Sp_Questions);
        final Button Submit = (Button)findViewById(R.id.Btn_Submit);
        ShoppingDB = new OnlineShopping_DB(this);
        final TextView passview = (TextView)findViewById(R.id.TV_pass) ;
        final TextView repassview = (TextView)findViewById(R.id.TV_Repass) ;
        final TextView phoneview = (TextView)findViewById(R.id.TV_Phone) ;



///////////////////// Date ////////////////////////////////////////////
        DateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(SignUpActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        DatesetOnDate,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        DatesetOnDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month +=1;
                Log.d(TAG,"onDateSet: mm/dd/yyyy: "+month+ "/" +dayOfMonth+ "/" +year+"" );
                String date = month + "/" +dayOfMonth+ "/" +year;
                DateView.setText(date);
            }
        };
 ////////////////////////////////// Button Click //////////////////////////////////
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txt_name.getText().toString().equals(" ")&&
                        !txt_email.getText().toString().equals("")&&
                        !txt_pass.getText().toString().equals("")&&
                        !txt_repass.getText().toString().equals("")&&
                        !txt_phone.getText().toString().equals("")&&
                        !txt_job.getText().toString().equals("")&&
                        !txt_Qanswer.getText().toString().equals("")&&
                        (C.isChecked()||F.isChecked())&&
                        !DateView.getText().toString().equals("Select Date ")&&
                        passview.getVisibility() == View.INVISIBLE &&
                        repassview.getVisibility() == View.INVISIBLE &&
                        phoneview.getVisibility() == View.INVISIBLE)
                {
                    String checked ;
                    if(C.isChecked())
                        checked = C.getText().toString();
                    else
                        checked = F.getText().toString();
                    Customer customer =new Customer(txt_name.getText().toString(),txt_email.getText().toString()
                    ,txt_phone.getText().toString(),txt_pass.getText().toString(),
                    DateView.getText().toString(),checked,txt_job.getText().toString(),
                    SQ.getSelectedItem().toString(),txt_Qanswer.getText().toString());
                    Toast.makeText(getApplicationContext(),txt_name+" "+txt_email+" "+txt_job+" "+txt_pass+" "+txt_Qanswer+" "+checked , Toast.LENGTH_LONG).show();

                    ShoppingDB.CreateNewCustomer(customer);

                    AlertDialog alertDialog = new AlertDialog.Builder(SignUpActivity.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Welcome "+txt_name.getText().toString()+" ");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    Intent homeactivity = new Intent(SignUpActivity.this , HomeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Name",txt_name.getText().toString());
                    bundle.putString("Email",txt_email.getText().toString());
                    bundle.putString("Phone",txt_phone.getText().toString());
                    bundle.putString("Birthday",DateView.getText().toString());
                    bundle.putString("Gender",checked);
                    homeactivity.putExtras(bundle);
                    startActivity(homeactivity);
                }
                else
                    Toast.makeText(getApplicationContext(),"Please Fill All Needed Information !" , Toast.LENGTH_LONG).show();

            }
        });

        /////////////////////////// Password textview////////////////////////////
        txt_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String regx = "^.{6,}$";
                Pattern pt = Pattern.compile(regx);
                Matcher mt = pt.matcher(txt_pass.getText());

                boolean result = mt.matches();
                if (result)
                    passview.setVisibility(TextView.INVISIBLE);
                else
                    passview.setVisibility(TextView.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        //////////////////////////////Re paasword textview////////////////////////
        txt_repass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String regx = txt_pass.getText().toString();
                String m = txt_repass.getText().toString();

                Pattern pt = Pattern.compile(regx);
                Matcher mt = pt.matcher(m);

                boolean result = mt.matches();
                if (result)
                {
                    repassview.setVisibility(TextView.INVISIBLE);
                }
                else
                {
                    repassview.setVisibility(TextView.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        /////////////////////////// phone textview ///////////////////////
        txt_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String regx = "^01[0-2][0-9]{8}$";
                Pattern pt = Pattern.compile(regx);
                Matcher mt = pt.matcher(txt_phone.getText());

                boolean result = mt.matches();
                if (result)
                    phoneview.setVisibility(TextView.INVISIBLE);
                else
                    phoneview.setVisibility(TextView.VISIBLE);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });


    }


}
