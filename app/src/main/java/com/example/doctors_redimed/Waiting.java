package com.example.doctors_redimed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Waiting extends AppCompatActivity {

    Button btlesson01;
    Button btlesson02;
    Button btBack;
    int received;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        //ánh xạ
        btlesson01 = (Button) findViewById(R.id.btlesson01);
        btlesson02 = (Button) findViewById(R.id.btlesson02);
        btBack = (Button) findViewById(R.id.btBack);
        //Nhận
        Bundle bd = getIntent().getExtras();
        if(bd!=null)
        {
            received = bd.getInt("RECEIVED");
            if(received == 1){
                btlesson02.setBackgroundResource(R.drawable.shapeoptionclick);
            }
        }
        //code
        btlesson01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it  =new Intent(Waiting.this,Review.class);
                startActivity(it);
            }
        });
        btlesson02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it  =new Intent(Waiting.this,Review.class);
                startActivity(it);
            }
        });
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it  =new Intent(Waiting.this,Option.class);
                startActivity(it);
            }
        });
    }
}
