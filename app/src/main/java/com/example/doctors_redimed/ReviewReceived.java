package com.example.doctors_redimed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReviewReceived extends AppCompatActivity {

    Button btSend;
    Button btCancel;
    int received=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_received);

        //ánh xạ
        btSend = (Button) findViewById(R.id.btSend);
        btCancel = (Button) findViewById(R.id.btCancel);


        //code
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                received=1;
                Intent it  =new Intent(ReviewReceived.this,Received.class);
                it.putExtra("RECEIVED",received);
                startActivity(it);
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it  =new Intent(ReviewReceived.this,Received.class);
                startActivity(it);
            }
        });
    }
}
