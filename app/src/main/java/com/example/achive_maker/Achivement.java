package com.example.achive_maker;

import android.content.Context;
import android.net.Uri;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Achivement {
    String picURI;
    String backURI;
    String textT = "Текст ачивки";
    String description;
    int pos;

    public Achivement(int pos, String picURI, String textT, String backURI, String description) {
        this.pos = pos;
        this.picURI = picURI;
        this.textT = textT;
        this.backURI = backURI;
        this.description = description;
    }
    public int getPosition(ArrayList<Achivement> achs){
        int pos = 0;
        for(int i =0;i<achs.size();i++){
            if(achs.get(i).picURI.equals(this.picURI)&&achs.get(i).textT.equals(this.textT)
                    &&achs.get(i).backURI.equals(this.backURI)&&achs.get(i).description.equals(this.description)){
                pos = i;
            }
        }
        return pos;
    }

}
