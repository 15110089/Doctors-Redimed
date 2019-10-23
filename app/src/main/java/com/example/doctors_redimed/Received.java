package com.example.doctors_redimed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Received extends AppCompatActivity {

    Button btlesson02;
    Button btBack;
    int received;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received);
        //ánh xạ
        btlesson02 = (Button) findViewById(R.id.btlesson02);
        btBack = (Button) findViewById(R.id.btBack);
        //Nhận
        Bundle bd = getIntent().getExtras();
        if(bd!=null)
        {
            received = bd.getInt("RECEIVED");
            if(received == 1){
                btlesson02.setVisibility(View.INVISIBLE);
            }
        }
        //code
        btlesson02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it  =new Intent(Received.this,ReviewReceived.class);
                startActivity(it);
            }
        });
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it  =new Intent(Received.this,Option.class);
                startActivity(it);
            }
        });
    }
}
