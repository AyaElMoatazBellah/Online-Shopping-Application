package com.example.online_shopping;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import java.util.Calendar;


public class fr_account extends Fragment implements AdapterView.OnItemSelectedListener{
    private static final String TAG ="fr_account";
    DatePickerDialog.OnDateSetListener DatesetOnDate ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_fr_account, container, false);
        final EditText name = (EditText)v.findViewById(R.id.ET_name);
        final EditText email = (EditText)v.findViewById(R.id.ET_email);
        final EditText birthday = (EditText)v.findViewById(R.id.ET_birthday);
        final EditText phone = (EditText)v.findViewById(R.id.ET_phone);
        final EditText password = (EditText)v.findViewById(R.id.ET_pass);
        final EditText job = (EditText)v.findViewById(R.id.ET_job);
        final Spinner gender = (Spinner) v.findViewById(R.id.SP_Gender);
        final Button update = (Button)v.findViewById(R.id.Btn_Update);
        final OnlineShopping_DB App_Database = new OnlineShopping_DB(getContext());

        SharedPreferences prefs = this.getActivity().getSharedPreferences("id", Context.MODE_PRIVATE);
        final int idName = prefs.getInt("id", 0); //0 is the default value.

        final Customer c = App_Database.GetCustomerInfo(idName);
        name.setText(c.getName());
        email.setText(c.getEmail());
        password.setText(c.getPassword());
        phone.setText(c.getPhone());
        job.setText(c.getJob());
        birthday.setText(c.getBirthday());
        if(c.getGender() == "Female")
            gender.setSelection(1);
        else if(c.getGender() == "Male")
            gender.setSelection(2);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Customer customer = new Customer(
                        name.getText().toString(),
                        email.getText().toString(),
                        phone.getText().toString(),
                        password.getText().toString(),
                        birthday.getText().toString(),
                        gender.getSelectedItem().toString(),
                        job.getText().toString(),
                        c.getSQuestion(),
                        c.getSAnswer());
                App_Database.UpdateCustomerInfo(idName,customer);
            }
        });

        ///////////////////// Date ////////////////////////////////////////////
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(),android.R.style.Theme_Holo_Light_Dialog_MinWidth,
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
                birthday.setText(date);
            }
        };

        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
