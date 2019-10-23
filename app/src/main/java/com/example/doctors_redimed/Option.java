package com.example.doctors_redimed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Option extends AppCompatActivity {

    Button btWaiting;
    Button btReceived;
    Button btTrack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        //ánh xạ
        btWaiting = (Button) findViewById(R.id.btWaiting);
        btReceived = (Button) findViewById(R.id.btReceived);
        btTrack = (Button) findViewById(R.id.btTrack);
        //code
        btWaiting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it  =new Intent(Option.this,Waiting.class);
                startActivity(it);
            }
        });
        btReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it  =new Intent(Option.this,Received.class);
                startActivity(it);
            }
        });
        btTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it  =new Intent(Option.this,Track.class);
                startActivity(it);
            }
        });
    }
}
