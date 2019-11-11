package com.example.doctors_redimed;

import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<ChildRequest> expandableListTitle;
    private HashMap<ChildRequest, List<ChildRequest>> expandableListDetail;

    public CustomExpandableListAdapter(Context context, List<ChildRequest> expandableListTitle,
                                       HashMap<ChildRequest, List<ChildRequest>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildRequest expandedListText = (ChildRequest) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = (TextView) convertView.findViewById(R.id.expandedListItem);
        TextView txtReceive = (TextView) convertView.findViewById(R.id.txtReceive);
        TextView txtSend = (TextView) convertView.findViewById(R.id.txtSend);
        TextView txtBack = (TextView) convertView.findViewById(R.id.txtBack);
        expandedListTextView.setText(expandedListText.Name1);
        txtReceive.setText(expandedListText.Name2);
        txtSend.setText(expandedListText.Name3);
        txtBack.setText(expandedListText.Name4);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ChildRequest listTitle = (ChildRequest) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.listTitle);
        TextView txtSdt = (TextView) convertView.findViewById(R.id.txtSdt);
        TextView txtGender = (TextView) convertView.findViewById(R.id.txtGender);
        TextView txtYear = (TextView) convertView.findViewById(R.id.txtYear);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle.Name1);
        txtSdt.setText(listTitle.Name2);
        txtGender.setText(listTitle.Name4);
        txtYear.setText(listTitle.Name3);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
