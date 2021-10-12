package com.example.online_shopping;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity {

    ProgressBar progressBar ;
    int Counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Prog();

    }
    public void Prog()
    {
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        final Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                progressBar.setProgress(Counter);
                TextView count = (TextView)findViewById(R.id.TV_Counter);
                count.setText(Counter+"%");
                Counter++;
                if(Counter == 100)
                {
                    Intent signin = new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(signin);
                    t.cancel();
                }

            }
        };
        t.schedule(tt,0,100);

    }
}
