package com.app.doctors_redimed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReviewReceived extends AppCompatActivity {

    Button btSend;
    Button btCancel;
    int received=0;
    ImageView Img1;
    ImageView imgBody;
    EditText txtQuestion1;
    TextView txtRegion;
    EditText edrFeedBack;
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
    String strKeyRequest;
    TextView txtMlFeelBack;
    Request rq;
    String user;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    String bKey;
    String bUSER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_received);
        Bundle bd = getIntent().getExtras();
        if(bd!=null)
        {
            bKey = bd.getString("KEY");
            bUSER = bd.getString("USER");

        }
        //ánh xạ
        btSend = (Button) findViewById(R.id.btSend);
        btCancel = (Button) findViewById(R.id.btCancel);
        Img1 = (ImageView) findViewById(R.id.idImg1);
        imgBody = (ImageView) findViewById(R.id.imgBody);
        txtRegion = (TextView) findViewById(R.id.txtRegion);
        edrFeedBack = (EditText) findViewById(R.id.edrFeedBack);
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
        txtMlFeelBack = (TextView) findViewById(R.id.txtMlFeelBack);
        databasel = new Database(this,"redimed.sqlite",null,1);
        Cursor itemTest = databasel.GetData("SELECT * FROM TabelUser WHERE Id = 1");
        while (itemTest.moveToNext()){
            user = itemTest.getString(1);
        }

        //code
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                received=1;
//                Intent it  =new Intent(ReviewReceived.this,Received.class);
//                it.putExtra("RECEIVED",received);
//                startActivity(it);
//                String[] keys = user.split("@");
//                String key = keys[0];
//                myRef.child("Doctor").child(key).child("Request").child(bKey).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
//                        NewRequest nrq = dataSnapshot.getValue(NewRequest.class);
//                        myRef.child("Patient").child(nrq.User).child("Request").child(nrq.Key).child("Feedback").setValue(edrFeedBack.getText().toString());
//                        Toast.makeText(ReviewReceived.this, "Feedback has been sent", Toast.LENGTH_LONG).show();
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                    }
//                });


                myRef.child("Patient").child(bUSER).child("Profile").child("Token").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                       String tikenUser = dataSnapshot.getValue(String.class);

                        myRef.child("Patient").child(bUSER).child("Request").child(bKey).child("Feedback").setValue(edrFeedBack.getText().toString());
                        //send notifycation
                        JSONObject postData = new JSONObject();
                        JSONObject notificationJS = new JSONObject();
                        JSONObject dataJS = new JSONObject();
                        try {
                            notificationJS.put("title", "Users-Redimed");
                            notificationJS.put("body", "Lesson "+ bKey +" has been responded by the doctor");
                            dataJS.put("value1", "UsersRedimed");
                            postData.put("to", tikenUser);
                            postData.put("collapse_key", "type_a");
                            postData.put("notification", notificationJS);
                            postData.put("data", dataJS);
                            new SendDeviceDetails().execute("https://fcm.googleapis.com/fcm/send", postData.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });


                String[] keys = user.split("@");
                String key = keys[0];

                myRef.child("Doctor").child(key).child("RequestReceived").child(bUSER).child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        String[] keys = user.split("@");
                        String key = keys[0];
                        NewRequest_Profile newRequest_Profile = dataSnapshot.getValue(NewRequest_Profile.class);
                        myRef.child("Doctor").child(key).child("RequestDone").child(bUSER).child("Profile").setValue(newRequest_Profile);

                        myRef.child("Doctor").child(key).child("RequestReceived").child(bUSER).child("Request").child(bKey).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                                String[] keys = user.split("@");
                                String key = keys[0];
                                NewRequest_Request newRequest_Request = dataSnapshot.getValue(NewRequest_Request.class);
                                myRef.child("Doctor").child(key).child("RequestDone").child(bUSER).child("Request").child(bKey).setValue(newRequest_Request);
                                Date currentTime = Calendar.getInstance().getTime();
                                SimpleDateFormat postFormater = new SimpleDateFormat("dd/MM/yyyy");
                                String newDateStr = postFormater.format(currentTime);
                                myRef.child("Doctor").child(key).child("RequestDone").child(bUSER).child("Request").child(bKey).child("Back").setValue(newDateStr);
                                myRef.child("Doctor").child(key).child("RequestReceived").child(bUSER).child("Request").child(bKey).removeValue();
                                myRef.child("Doctor").child(key).child("RequestReceived").child(bUSER).child("Request").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                                        String[] keys = user.split("@");
                                        String key = keys[0];
                                        long keyRequest =  dataSnapshot.getChildrenCount();
                                        if(keyRequest<1)
                                            myRef.child("Doctor").child(key).child("RequestReceived").child(bUSER).removeValue();
                                        Intent it  =new Intent(ReviewReceived.this,MainTab.class);
                                        startActivity(it);
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });


            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it  =new Intent(ReviewReceived.this,MainTab.class);
                startActivity(it);
            }
        });
        String[] keys = user.split("@");
        String key = keys[0];

                myRef.child("Patient").child(bUSER).child("Request").child(bKey).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        rq = dataSnapshot.getValue(Request.class);
                        if(rq.State.equals("5")){
                            txtMlFeelBack.setText("Feedback of Machine Learning: disease rate less than 50% (normal skin)");
                        }else{
                            if(rq.State.equals("6")){
                                txtMlFeelBack.setText("Feedback of Machine Learning: disease rate greater than 50% (skin cancer)");
                            }else{
                                //machine learn
                                String path = "https://nghiagood.pythonanywhere.com\\";
                                String path2 = rq.LinkImage1;
                                try {
                                    String encodedURL = URLEncoder.encode(path2.replace('/', '\\'), "UTF-8");
                                    new ReviewReceived.ReadJSONObject().execute(path + encodedURL);
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
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
                        Picasso.get().load(rq.LinkImage1).into(Img1);
                        if(rq.Region.equals("Right Hand Front")){
                            imgBody.setImageResource(R.drawable.body_f_1);
                        }
                        if(rq.Region.equals("Left Hand Front")){
                            imgBody.setImageResource(R.drawable.body_f_2);
                        }
                        if(rq.Region.equals("Body Front")){
                            imgBody.setImageResource(R.drawable.body_f_3);
                        }
                        if(rq.Region.equals("Right Foot Front")){
                            imgBody.setImageResource(R.drawable.body_f_4);
                        }
                        if(rq.Region.equals("Left Foot Front")){
                            imgBody.setImageResource(R.drawable.body_f_5);
                        }
                        if(rq.Region.equals("Head Front")){
                            imgBody.setImageResource(R.drawable.body_f_6);
                        }

                        if(rq.Region.equals("Right Hand Back")){
                            imgBody.setImageResource(R.drawable.body_b_1);
                        }
                        if(rq.Region.equals("Left Hand Back")){
                            imgBody.setImageResource(R.drawable.body_b_2);
                        }
                        if(rq.Region.equals("Body Back")){
                            imgBody.setImageResource(R.drawable.body_b_3);
                        }
                        if(rq.Region.equals("Right Foot Back")){
                            imgBody.setImageResource(R.drawable.body_b_4);
                        }
                        if(rq.Region.equals("Left Foot Back")){
                            imgBody.setImageResource(R.drawable.body_b_5);
                        }
                        if(rq.Region.equals("Head Back")){
                            imgBody.setImageResource(R.drawable.body_b_6);
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });



    }
    private class ReadJSONObject extends AsyncTask<String, Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuffer content = new StringBuffer();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
                bufferedReader.close();

            } catch (MalformedURLException e) {
                Log.d(">>>>>>>>>>", "Ko on roi 1");
                e.printStackTrace();
            } catch (IOException e) {
                Log.d(">>>>>>>>>>", "Ko on roi 2");
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);

                if(object.getString("Label").equals("1"))
                {
                   // myRef.child("Patient").child(bUSER).child("Request").child(bKey).child("Feedback").setValue("Feedback of Machine Learning: disease rate > 50% (skin cancer)");
                    txtMlFeelBack.setText("Feedback of Machine Learning: disease rate greater than 50% (skin cancer)");
                    myRef.child("Patient").child(bUSER).child("Request").child(bKey).child("State").setValue("6");
                }
                else{
                   // myRef.child("Patient").child(bUSER).child("Request").child(bKey).child("Feedback").setValue("Feedback of Machine Learning: disease rate < 50% (normal skin)");
                    txtMlFeelBack.setText("Feedback of Machine Learning: disease rate less than 50% (normal skin)");
                    myRef.child("Patient").child(bUSER).child("Request").child(bKey).child("State").setValue("5");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //them ket luan may hoc voa firebase

        }
    }

    private class SendDeviceDetails extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String data = "";
            HttpURLConnection httpURLConnection = null;
            try {
                Log.i(">1<",params[1]);
                httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type","application/json");
                httpURLConnection.setRequestProperty("Authorization","key=AAAAmA8JyIM:APA91bE-VyD1vFhAbXi9y1sCn94wpY6fcpYU3hIjsxpVx91yRsa9T8I0JaoAp7YuaqWV-sRTg2CWdD8rodVkdPSd3YTDkLneK7DPzGkSiLM6Y1c3rtQYJ5PtIfCKSFXL5ICzE6of0OEB");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter( new OutputStreamWriter(outputStream, "UTF-8") );
                bufferedWriter.write(params[1]);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream, "iso-8859-1") );
                StringBuilder result = new StringBuilder();
                String line = "";
                while( (line = bufferedReader.readLine()) != null ){
                    result.append(line);
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
            return data;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
        }
    }
}
