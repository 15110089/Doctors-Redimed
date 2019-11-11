package com.example.doctors_redimed;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TabDone extends Fragment {
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<ChildRequest> expandableListTitle;
    int lastExpandedPosition = 0;
    int nodeIsOpening;
    Database databasel;
    String user;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    HashMap<ChildRequest, List<ChildRequest>> expandableListDetail = new HashMap<ChildRequest, List<ChildRequest>>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_tab_done, container, false);
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        databasel = new Database(getContext(),"redimed.sqlite",null,1);
        Cursor itemTest = databasel.GetData("SELECT * FROM TabelUser WHERE Id = 1");
        while (itemTest.moveToNext()){
            user = itemTest.getString(1);
        }
        //
        String[] keys = user.split("@");
        String key = keys[0];
        myRef.child("Doctor").child(key).child("RequestDone").addValueEventListener(new ValueEventListener() {
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
                        nodeChild.Name3 = "Receive: " + nodeC.child("Receive").getValue(String.class);
                        nodeChild.Name4 = "Back: "+nodeC.child("Back").getValue(String.class);;
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

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
                nodeIsOpening = groupPosition;

            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
//
//                Intent it  =new Intent(getActivity(),ReviewReceived.class);
//
//                it.putExtra("KEY",expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition).User );
//                it.putExtra("USER",expandableListTitle.get(nodeIsOpening).User );
//                startActivity(it);
//

                return false;
            }
        });
        return view;
    }
}
