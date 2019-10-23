package com.example.doctors_redimed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Review extends AppCompatActivity {

    Button btReceive;
    Button btCancel;
    int received=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        //ánh xạ
        btReceive = (Button) findViewById(R.id.btReceive);
        btCancel = (Button) findViewById(R.id.btCancel);

        //code
        btReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                received = 1;
//              btlesson01.setBackgroundResource(R.drawable.shapeoptionclick);
                Intent it  =new Intent(Review.this,Waiting.class);
                it.putExtra("RECEIVED",received);
                startActivity(it);
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                btlesson01.setBackgroundResource(R.drawable.shapeoptionclick);
                Intent it  =new Intent(Review.this,Waiting.class);
                startActivity(it);
            }
        });
    }
}
