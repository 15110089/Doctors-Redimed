package com.example.doctors_redimed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
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

public class Waiting extends AppCompatActivity {

    Button btBack;
//    int received;
    ListView listViewWaiting;
    Database databasel;
    String user;
    ArrayList<String> nameNote;
    ArrayList<NewRequest>arlKeyRequest;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        //ánh xạ
        listViewWaiting = (ListView) findViewById(R.id.listViewWaiting);
        databasel = new Database(this,"redimed.sqlite",null,1);
        Cursor itemTest = databasel.GetData("SELECT * FROM TabelUser WHERE Id = 1");
        while (itemTest.moveToNext()){
            user = itemTest.getString(1);
        }
        btBack = (Button) findViewById(R.id.btBack);
        //Nhận
        Bundle bd = getIntent().getExtras();
//        if(bd!=null)
//        {
//            received = bd.getInt("RECEIVED");
//            if(received == 1){
////                btlesson02.setBackgroundResource(R.drawable.shapeoptionclick);
//            }
//        }
        //code

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it  =new Intent(Waiting.this,Option.class);
                startActivity(it);
            }
        });
        //
        String[] keys = user.split("@");
        String key = keys[0];
        myRef.child("NewRequest").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                ArrayList<ItemWaiting> arlWaiting = new ArrayList<>();
                arlKeyRequest = new ArrayList<>();
                nameNote = new ArrayList<>();
                for(DataSnapshot node:dataSnapshot.getChildren()) {
                    NewRequest newRequest = node.getValue(NewRequest.class);

                    ItemWaiting item = new ItemWaiting(newRequest.User,newRequest.Key);
                    arlWaiting.add(item);
                    nameNote.add(node.getKey());
                    arlKeyRequest.add(newRequest);
                }
                AdapterWaiting adapter = new AdapterWaiting(Waiting.this,R.layout.item_waiting,arlWaiting);
                listViewWaiting.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        listViewWaiting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it  =new Intent(Waiting.this,Review.class);

                it.putExtra("KEY",arlKeyRequest.get(position).Key );
                it.putExtra("USER",arlKeyRequest.get(position).User);
                it.putExtra("NAMENOTE",nameNote.get(position) );
                startActivity(it);
            }
        });
        //
    }
}
