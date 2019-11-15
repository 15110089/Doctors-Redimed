package com.app.doctors_redimed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent it  =new Intent(MainActivity.this,Login.class);
//                Intent it  =new Intent(MainActivity.this,Example.class);
//                Intent it  =new Intent(MainActivity.this,MainTab.class);
                startActivity(it);
            }
        }, 1000);
    }
}
