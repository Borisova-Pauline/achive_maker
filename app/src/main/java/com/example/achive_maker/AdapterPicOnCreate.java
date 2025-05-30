package com.example.achive_maker;

import static com.example.achive_maker.MainActivity.SHAR_PREFS_NAME;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdapterPicOnCreate extends BaseAdapter {
    private LayoutInflater LInflater;
    private ArrayList<ImageView> list;
    private Context context;
    ImageView picture;
    public AdapterPicOnCreate(Context context, ArrayList<ImageView> data, ImageView picture){
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
        AdapterPicOnCreate.ViewHolder holder;
        View v = convertView;
        if ( v == null){
            holder = new AdapterPicOnCreate.ViewHolder();
            v = LInflater.inflate(R.layout.gridview, parent, false);
            holder.image_pic = (ImageView) v.findViewById(R.id.picture_ex);
            v.setTag(holder);
        }

        holder = (AdapterPicOnCreate.ViewHolder) v.getTag();
        ImageView pics = getData(position);

        holder.image_pic.setImageURI((Uri) list.get(position).getTag());

        holder.image_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    picture.setImageURI((Uri) list.get(position).getTag());
                    picture.setTag((list.get(position).getTag()));
                }catch (Exception ex){
                    ex.getMessage();
                }
            }
        });

        return v;
    }
    SharedPreferences sp;
    ImageView getData(int position){
        return (getItem(position));
    }
    private static class ViewHolder {
        private ImageView image_pic;
    }
}
