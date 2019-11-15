package com.app.doctors_redimed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button btLoginId;
    TextView txtEmail;
    TextView txtPass;
    Database databasel;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //ánh xạ
        btLoginId = (Button) findViewById(R.id.btLoginId);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtPass = (TextView) findViewById(R.id.txtPass);
        databasel = new Database(this,"redimed.sqlite",null,1);

        //code
        btLoginId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] keys = txtEmail.getText().toString().split("@");
                String key = keys[0];
                myRef.child("Doctor").child(key).child("Profile").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        User u = dataSnapshot.getValue(User.class);
                        try
                        {
                            String passWord = u.Pass;
                            if(passWord.equals(""))
                            { }
                            else
                            {
                                if(txtPass.getText().toString().equals(passWord))
                                {
//                                    Intent it  =new Intent(Login.this,Option.class);
                                    Intent it  =new Intent(Login.this,MainTab.class);
                                    //create table
                                    databasel.QueryData("CREATE TABLE IF NOT EXISTS TabelUser(Id INTEGER PRIMARY KEY, Email VARCHAR(200))");
                                    Cursor itemTests = databasel.GetData("SELECT * FROM TabelUser");
                                    while (itemTests.moveToNext()){
                                        databasel.QueryData("UPDATE TabelUser SET Email ='"+ u.Email +"' WHERE Id = 1");
                                        startActivity(it);
                                        return;
                                    }
                                    databasel.QueryData("INSERT INTO TabelUser VALUES(1,'"+u.Email+"')");
                                    startActivity(it);
                                    return;
                                }
                                else
                                {
                                    Toast.makeText(Login.this, "Email hoặc Password không đúng", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(Login.this, "Email hoặc Password không đúng", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            }
        });
    }
}
