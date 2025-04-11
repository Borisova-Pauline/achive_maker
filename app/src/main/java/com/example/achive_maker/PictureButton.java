package com.example.achive_maker;

import android.widget.ImageView;

public class PictureButton implements GridItem {
    ImageView iv;
    int picID = R.drawable.unknow_pic;
    public PictureButton(){

    }
    public PictureButton(int picID){
        this.picID=picID;
    }

    public long id;
    public String imgUrl;

    public void setId(long id) {
        this.id = id;
    }
    public String getImgUrl() {
        return imgUrl;
    }
}
