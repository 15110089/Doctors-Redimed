package com.example.doctors_redimed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Track extends AppCompatActivity {

    Button btlesson02;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        //ánh xạ
        btlesson02 = (Button) findViewById(R.id.btlesson02);
        //code
        btlesson02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it  =new Intent(Track.this,ReviewTrack.class);
                startActivity(it);
            }
        });
    }
}
