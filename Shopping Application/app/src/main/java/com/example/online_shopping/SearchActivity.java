package com.example.online_shopping;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT =1000 ;
    RecyclerView products_RV;
    EditText SearchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        products_RV = (RecyclerView)findViewById(R.id.RV_Products);
        SearchBar = (EditText)findViewById(R.id.SearchBar);
        final OnlineShopping_DB App_Database = new OnlineShopping_DB(this);
        final Resources resources = getApplicationContext().getResources();
        final ArrayList<Product> ProductsList = new ArrayList<>();
        final ImageButton Btn_BarCode = (ImageButton)findViewById(R.id.Btn_BarcodeSearch);
        final ImageButton Btn_VoiceSearch = (ImageButton)findViewById(R.id.Btn_VoiceSearch);


        SearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Cursor ProductCU = App_Database.RetriveProducts(SearchBar.getText().toString());
                ProductsList.clear();
                while ( !ProductCU.isAfterLast())
                {
                    Product p = new Product();
                    p.setId(Integer.parseInt(ProductCU.getString(0)));
                    int imageid = resources.getIdentifier("p"+p.getId()+"","drawable",getApplicationContext().getPackageName());
                    p.setImageID(imageid);
                    p.setProname(ProductCU.getString(1));
                    p.setProDesc(ProductCU.getString(2));
                    p.setPrice(Integer.parseInt(ProductCU.getString(3)));
                    p.setQuantity(Integer.parseInt(ProductCU.getString(4)));
                    p.setCatID(Integer.parseInt(ProductCU.getString(5)));

                    ProductsList.add(p);
                    ProductCU.moveToNext();
                }
                MyAdapter myAdapter = new MyAdapter(getApplicationContext(), ProductsList);
                products_RV.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
                products_RV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        Btn_BarCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanCode();
            }
        });

        Btn_VoiceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartVoiceScan();
            }
        });



    }
    public  void ScanCode()
    {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning Code Please Wait ...");
        integrator.initiateScan();
    }
    public void StartVoiceScan()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Tell Me Product Name");
        try {
            startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Wrong Voice !" ,Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(result.getContents());
                builder.setTitle("Your Scaned Code Is ");
                builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ScanCode();
                    }
                }).setNegativeButton("Finish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                SearchBar.setText("");
                SearchBar.setText(result.getContents());
            }
            else
                Toast.makeText(getApplicationContext(),"No Data",Toast.LENGTH_LONG).show();
        }
        else
        {
            super.onActivityResult(requestCode,resultCode,data);
        }
        if(requestCode == REQUEST_CODE_SPEECH_INPUT)
        {
                  if (resultCode == RESULT_OK && null != data) {
                      ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                      SearchBar.setText(matches.get(0));
                  }

        }

    }
}
