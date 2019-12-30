package com.codingelab.tutorial;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends ArrayAdapter {

    List list=new ArrayList();


    public ListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    static class LayoutHandler
    {

        TextView ID,NAME,MOBLIE,EMAIL;

    }
    @Override
    public void add(@Nullable Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row=convertView;
        LayoutHandler layoutHandler;
        if(row==null)
        {

            LayoutInflater layoutInflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.row_layout,parent,false);
            layoutHandler = new LayoutHandler();
            layoutHandler.ID=(TextView)row.findViewById(R.id.IV_title_o);
            layoutHandler.NAME=(TextView)row.findViewById(R.id.IV_title);
            layoutHandler.MOBLIE=(TextView)row.findViewById(R.id.IV_deascription);
            layoutHandler.EMAIL=(TextView)row.findViewById(R.id.IV_deascription_3);
            row.setTag(layoutHandler);
        }
        else
        {

            layoutHandler=(LayoutHandler)row.getTag();


        }

        DataProvider dataProvider=(DataProvider)this.getItem(position);
        layoutHandler.ID.setText(dataProvider.getId());
        layoutHandler.NAME.setText(dataProvider.getName());
        layoutHandler.MOBLIE.setText(dataProvider.getMoblie());
        layoutHandler.EMAIL.setText(dataProvider.getEmail());

        return row;
    }
}
