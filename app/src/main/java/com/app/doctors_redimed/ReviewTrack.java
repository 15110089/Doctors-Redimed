package com.app.doctors_redimed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ReviewTrack extends AppCompatActivity {

    Button btBack;
    ImageView Img1;
    EditText txtQuestion1;
    TextView txtRegion;
    TextView txtFeelBack;
    EditText txtQuestion2;
    EditText txtQuestion3;
    EditText txtQuestion4;
    CheckBox cbQuestion1;
    CheckBox cbQuestion2;
    CheckBox cbQuestion3;
    CheckBox cbQuestion4;
    CheckBox cbQuestion5;
    CheckBox cbQuestion6;
    CheckBox cbQuestion7;
    CheckBox cbQuestion8;
    CheckBox cbQuestion9;
    CheckBox cbQuestion10;
    CheckBox cbQuestion11;
    Database databasel;
    String user;
    String bUser;
    String bKey;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_track);
        Bundle bd = getIntent().getExtras();
        if(bd!=null)
        {
            bKey = bd.getString("KEY");
            bUser = bd.getString("USER");
            Log.i(">1<",bKey+bUser);
        }
        btBack = (Button) findViewById(R.id.btBack);
        Img1 = (ImageView) findViewById(R.id.idImg1);
        txtRegion = (TextView) findViewById(R.id.txtRegion);
        txtFeelBack = (TextView) findViewById(R.id.txtFeelBack);
        txtQuestion1 = (EditText) findViewById(R.id.txtQuestion1);
        txtQuestion2 = (EditText) findViewById(R.id.txtQuestion2);
        txtQuestion3 = (EditText) findViewById(R.id.txtQuestion3);
        txtQuestion4 = (EditText) findViewById(R.id.txtQuestion4);
        cbQuestion1 = (CheckBox) findViewById(R.id.cbQuestion1);
        cbQuestion2 = (CheckBox) findViewById(R.id.cbQuestion2);
        cbQuestion3 = (CheckBox) findViewById(R.id.cbQuestion3);
        cbQuestion4 = (CheckBox) findViewById(R.id.cbQuestion4);
        cbQuestion5 = (CheckBox) findViewById(R.id.cbQuestion5);
        cbQuestion6 = (CheckBox) findViewById(R.id.cbQuestion6);
        cbQuestion7 = (CheckBox) findViewById(R.id.cbQuestion7);
        cbQuestion8 = (CheckBox) findViewById(R.id.cbQuestion8);
        cbQuestion9 = (CheckBox) findViewById(R.id.cbQuestion9);
        cbQuestion10 = (CheckBox) findViewById(R.id.cbQuestion10);
        cbQuestion11 = (CheckBox) findViewById(R.id.cbQuestion11);
        databasel = new Database(this,"redimed.sqlite",null,1);
        Cursor itemTest = databasel.GetData("SELECT * FROM TabelUser WHERE Id = 1");
        while (itemTest.moveToNext()){
            user = itemTest.getString(1);
        }
        //code

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                btlesson01.setBackgroundResource(R.drawable.shapeoptionclick);
                Intent it  =new Intent(ReviewTrack.this,MainTab.class);
                startActivity(it);
            }
        });
        myRef.child("Patient").child(bUser).child("Request").child(bKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                Request rq = dataSnapshot.getValue(Request.class);
                if(rq.Question5.equals("1"))
                    cbQuestion1.setChecked(true);
                if(rq.Question6.equals("1"))
                    cbQuestion2.setChecked(true);
                if(rq.Question7.equals("1"))
                    cbQuestion3.setChecked(true);
                if(rq.Question8.equals("1"))
                    cbQuestion4.setChecked(true);
                if(rq.Question9.equals("1"))
                    cbQuestion5.setChecked(true);
                if(rq.Question10.equals("1"))
                    cbQuestion6.setChecked(true);
                if(rq.Question11.equals("1"))
                    cbQuestion7.setChecked(true);
                if(rq.Question12.equals("1"))
                    cbQuestion8.setChecked(true);
                if(rq.Question13.equals("1"))
                    cbQuestion9.setChecked(true);
                if(rq.Question14.equals("1"))
                    cbQuestion10.setChecked(true);
                if(rq.Question15.equals("1"))
                    cbQuestion11.setChecked(true);
                txtQuestion1.setText(rq.Question1);
                txtQuestion2.setText(rq.Question2);
                txtQuestion3.setText(rq.Question3);
                txtQuestion4.setText(rq.Question4);
                txtRegion.setText("Region: "+rq.Region);
                txtFeelBack.setText("Feedback: "+rq.Feedback);
                Picasso.get().load(rq.LinkImage1).into(Img1);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}