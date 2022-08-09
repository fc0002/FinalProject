package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String myPreferences = "File";
    private static final String Search = "Search";
    private Button searchBtn;

    int progress = 0;
    ProgressBar simpleProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        simpleProgressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
        searchBtn = (Button) findViewById(R.id.btnSearch);

        searchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setProgressValue(progress);

                Intent goToProfile = new Intent(MainActivity.this, SearchList.class);
                startActivity(goToProfile);

            }

            private void setProgressValue(final int progress) {

                simpleProgressBar.setProgress(progress);
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        setProgressValue(progress + 10);
                    }
                });
                thread.start();
            }

        });
    }
}
