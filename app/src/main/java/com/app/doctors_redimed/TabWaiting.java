package com.app.doctors_redimed;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TabWaiting extends Fragment {
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<ChildRequest> expandableListTitle;
    int lastExpandedPosition = 0;
    Button btReceive;
    int nodeIsOpening;
    Database databasel;
    String user;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    HashMap<ChildRequest, List<ChildRequest>> expandableListDetail = new HashMap<ChildRequest, List<ChildRequest>>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_tab_waiting, container, false);

        btReceive = (Button) view.findViewById(R.id.btReceive);
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        databasel = new Database(getContext(),"redimed.sqlite",null,1);
        Cursor itemTest = databasel.GetData("SELECT * FROM TabelUser WHERE Id = 1");
        while (itemTest.moveToNext()){
            user = itemTest.getString(1);
        }
        //

        myRef.child("NewRequest").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                expandableListDetail.clear();
                for (DataSnapshot node: dataSnapshot.getChildren() ) {
                    ChildRequest nodeParent = new ChildRequest();
                    expandableListTitle = new ArrayList<ChildRequest>();
                    nodeParent.Name1 = node.child("Profile").child("Name").getValue(String.class);
                    nodeParent.Name2 = node.child("Profile").child("Phone").getValue(String.class);
                    nodeParent.Name3 = node.child("Profile").child("Birth").getValue(String.class);
                    nodeParent.Name4 = node.child("Profile").child("Gender").getValue(String.class);
                    nodeParent.User = node.getKey();
                    List<ChildRequest> lsNodeChild = new ArrayList<ChildRequest>();
                    for (DataSnapshot nodeC: node.child("Request").getChildren() ){
                        ChildRequest nodeChild = new ChildRequest();
                        nodeChild.Name1 = nodeC.child("Name").getValue(String.class);
                        nodeChild.Name2 = "Send: " + nodeC.child("Send").getValue(String.class);
                        nodeChild.Name3 = "";
                        nodeChild.Name4 = "";
                        nodeChild.User = nodeC.getKey();
                        lsNodeChild.add(nodeChild);
                    }
                    expandableListDetail.put(nodeParent,lsNodeChild);

                    expandableListTitle = new ArrayList<ChildRequest>(expandableListDetail.keySet());
                    expandableListAdapter = new CustomExpandableListAdapter(getContext(), expandableListTitle, expandableListDetail);
                    expandableListView.setAdapter(expandableListAdapter);
                }





            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
//


//
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
                btReceive.setVisibility(View.VISIBLE);
                nodeIsOpening = groupPosition;

                Log.i(">1<",String.valueOf(nodeIsOpening));



            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                btReceive.setVisibility(View.INVISIBLE);

            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
//                Toast.makeText(
//                        getApplicationContext(),
//                        expandableListTitle.get(groupPosition)
//                                + " -> "
//                                + expandableListDetail.get(
//                                expandableListTitle.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT
//                ).show();
                Intent it  =new Intent(getActivity(),Review.class);

                it.putExtra("KEY",expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition).User  );
                it.putExtra("USER",expandableListTitle.get(nodeIsOpening).User);
                startActivity(it);
                return false;
            }
        });

        btReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(">1<", expandableListTitle.get(nodeIsOpening).User);
                String[] keys = user.split("@");
                String key = keys[0];
                NewRequest_Profile newRequest_Profile = new  NewRequest_Profile(expandableListTitle.get(nodeIsOpening).Name1,expandableListTitle.get(nodeIsOpening).Name3,expandableListTitle.get(nodeIsOpening).Name2,expandableListTitle.get(nodeIsOpening).Name4);
                myRef.child("Doctor").child(key).child("RequestReceived").child( expandableListTitle.get(nodeIsOpening).User).child("Profile").setValue(newRequest_Profile);

                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat postFormater = new SimpleDateFormat("dd/MM/yyyy");
                String newDateStr = postFormater.format(currentTime);

                for (ChildRequest node :expandableListDetail.get(expandableListTitle.get(nodeIsOpening))) {
                    NewRequest_Request newRequest_Request = new NewRequest_Request(node.Name1,node.Name2,newDateStr);
                    myRef.child("Doctor").child(key).child("RequestReceived").child( expandableListTitle.get(nodeIsOpening).User).child("Request").child(node.User).setValue(newRequest_Request);
                }
                myRef.child("NewRequest").child(expandableListTitle.get(nodeIsOpening).User).removeValue();


            }
        });







        return view;
    }
}
