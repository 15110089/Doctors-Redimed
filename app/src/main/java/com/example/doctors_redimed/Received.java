package com.example.doctors_redimed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Received extends AppCompatActivity {

//    Button btlesson02;
    Button btBack;
    Database databasel;
    ArrayList<String>arlKeyRequest;
    String user;
    ListView listViewReceive;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    int received;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received);
        //ánh xạ
//        btlesson02 = (Button) findViewById(R.id.btlesson02);
        listViewReceive = (ListView) findViewById(R.id.listViewReceive);
        btBack = (Button) findViewById(R.id.btBack);
        databasel = new Database(this,"redimed.sqlite",null,1);
        Cursor itemTest = databasel.GetData("SELECT * FROM TabelUser WHERE Id = 1");
        while (itemTest.moveToNext()){
            user = itemTest.getString(1);
        }
        //Nhận
//        Bundle bd = getIntent().getExtras();
//        if(bd!=null)
//        {
//            received = bd.getInt("RECEIVED");
//            if(received == 1){
//                btlesson02.setVisibility(View.INVISIBLE);
//            }
//        }
        //code
//        btlesson02.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent it  =new Intent(Received.this,ReviewReceived.class);
//                startActivity(it);
//            }
//        });
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it  =new Intent(Received.this,Option.class);
                startActivity(it);
            }
        });
        //
        String[] keys = user.split("@");
        String key = keys[0];
        myRef.child("Doctor").child(key).child("Request").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                ArrayList<ItemWaiting> arlWaiting = new ArrayList<>();
                arlKeyRequest = new ArrayList<>();
                for(DataSnapshot node:dataSnapshot.getChildren()) {
                    NewRequest newRequest = node.getValue(NewRequest.class);
                    ItemWaiting item = new ItemWaiting(newRequest.User,newRequest.Key);
                    arlWaiting.add(item);
                    arlKeyRequest.add(node.getKey());
                }
                AdapterWaiting adapter = new AdapterWaiting(Received.this,R.layout.item_waiting,arlWaiting);
                listViewReceive.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        listViewReceive.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it  =new Intent(Received.this,ReviewReceived.class);
                it.putExtra("KEY",arlKeyRequest.get(position) );
                startActivity(it);
            }
        });
        //
    }
}
