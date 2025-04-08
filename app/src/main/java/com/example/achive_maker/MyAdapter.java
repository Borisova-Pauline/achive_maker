package com.example.achive_maker;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private LayoutInflater LInflater;
    private ArrayList<Achivement> list;
    public MyAdapter(Context context, ArrayList<Achivement> data){

        list = data;
        LInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public Achivement getItem(int position) {
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        View v = convertView;
        if ( v == null){
            holder = new ViewHolder();
            v = LInflater.inflate(R.layout.listview, parent, false);
            holder.image_ach = (ImageView) v.findViewById(R.id.picture_cr_ex);
            holder.text = (EditText) v.findViewById(R.id.naming_cr_ex);
            holder.backg = ((ImageView) v.findViewById(R.id.background_cr_ex));
            v.setTag(holder);
        }

        holder = (ViewHolder) v.getTag();
        Achivement achs = getData(position);

        holder.image_ach.setImageResource(achs.picID);
        holder.text.setText(achs.textT);
        holder.backg.setImageResource(achs.backID);

        return v;
    }
    Achivement getData(int position){
        return (getItem(position));
    }
    private static class ViewHolder {
        private ImageView image_ach;
        private EditText text;
        private ImageView backg;
    }
}
