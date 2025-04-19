package com.example.achive_maker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class AdapterBackOnCreate extends BaseAdapter {
    private LayoutInflater LInflater;
    private ArrayList<ImageView> list;
    private Context context;
    ImageView picture;
    public AdapterBackOnCreate(Context context, ArrayList<ImageView> data, ImageView picture){
        this.context = context;
        list = data;
        LInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.picture = picture;
    }

    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public ImageView getItem(int position) {
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterBackOnCreate.ViewHolder holder;
        View v = convertView;
        if ( v == null){
            holder = new AdapterBackOnCreate.ViewHolder();
            v = LInflater.inflate(R.layout.backg_listview, parent, false);
            holder.image_back = (ImageView) v.findViewById(R.id.backg_ex);
            v.setTag(holder);
        }

        holder = (AdapterBackOnCreate.ViewHolder) v.getTag();
        ImageView backs = getData(position);

        holder.image_back.setImageURI((Uri) list.get(position).getTag());

        holder.image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    picture.setImageURI((Uri) list.get(position).getTag());
                }catch (Exception ex){
                    ex.getMessage();
                }
            }
        });

        return v;
    }
    ImageView getData(int position){
        return (getItem(position));
    }
    private static class ViewHolder {
        private ImageView image_back;
    }
}