package com.example.doctors_redimed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterWaiting extends BaseAdapter {
    public Context context;
    public int layout;
    public List<ItemWaiting> listItems;

    public AdapterWaiting(Context context, int layout, List<ItemWaiting> listItems) {
        this.context = context;
        this.layout = layout;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);
        TextView txtName =(TextView) convertView.findViewById(R.id.txtName);
        TextView txtSTT =(TextView) convertView.findViewById(R.id.txtSTT);
        ItemWaiting item = listItems.get(position);
        txtName.setText(item.Name);
        txtSTT.setText(item.STT);
        return convertView;
    }

}
