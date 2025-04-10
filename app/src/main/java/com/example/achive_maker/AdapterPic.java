package com.example.achive_maker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class AdapterPic extends BaseAdapter {
    private LayoutInflater LInflater;
    private ArrayList<PictureButton> list;
    private Context context;
    public AdapterPic(Context context, ArrayList<PictureButton> data){
        this.context = context;
        list = data;
        LInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public PictureButton getItem(int position) {
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterPic.ViewHolder holder;
        View v = convertView;
        if ( v == null){
            holder = new AdapterPic.ViewHolder();
            v = LInflater.inflate(R.layout.gridview, parent, false);
            holder.image_pic = (ImageView) v.findViewById(R.id.picture_ex);
            v.setTag(holder);
        }

        holder = (AdapterPic.ViewHolder) v.getTag();
        PictureButton pics = getData(position);

        holder.image_pic.setImageResource(pics.picID);

        if(pics.picID == R.drawable.picture_plus){
            holder.image_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {                 ///////////////////////////////////////////   OVER HERE!!
                    Toast.makeText(context, "adding a picture", Toast.LENGTH_LONG).show();

                }
            });
        }else{
            holder.image_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, LookOrDel.class);
                    intent.putExtra("picture", pics.picID);
                    context.startActivity(intent);
                    if(context instanceof Activity){
                        ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }

                }
            });
        }

        return v;
    }
    PictureButton getData(int position){
        return (getItem(position));
    }
    private static class ViewHolder {
        private ImageView image_pic;
    }

    /*public static final int GET_FROM_GALLERY = 3;
    public void loadImageFromGallery(View view) {
        ((Activity) context).startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImage);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }*/
}
