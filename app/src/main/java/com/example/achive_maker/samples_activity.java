package com.example.achive_maker;

import static com.example.achive_maker.MainActivity.SHAR_PREFS_NAME;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class samples_activity extends AppCompatActivity {
    SharedPreferences sharPrefs;
    private boolean isFirstClicked = false;
    private boolean isSecondClicked = false;
    ArrayList<ImageView> pics = new ArrayList<>();
    ArrayList<ImageView> backs = new ArrayList<>();
    Context context = this;
    static int plusWhat = 0;

    public void toMainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    /*public void toLookDelMenu(View view){
        Intent intent = new Intent(this, LookOrDel.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_samples);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sharPrefs = getSharedPreferences(SHAR_PREFS_NAME, MODE_PRIVATE);
        isFirstClicked=sharPrefs.getBoolean("firstInSample", false);
        isSecondClicked=sharPrefs.getBoolean("secondInSample", false);

        View arrow1 = findViewById(R.id.arrow_pic);
        GridView tv1 = findViewById(R.id.picture_grid);
        changeButton((ImageView) arrow1, isFirstClicked, tv1);

        View arrow2 = findViewById(R.id.arrow_fr);
        ListView tv2 = findViewById(R.id.backg_list);
        changeButton((ImageView) arrow2, isSecondClicked, tv2);


        AdapterPic myAdPic = new AdapterPic(this, pics);
        pics.add(new ImageView(this));
        tv1.setAdapter(myAdPic);

        AdapterBackgr myAdBack = new AdapterBackgr(this, backs);
        backs.add(new ImageView(this));
        tv2.setAdapter(myAdBack);

        ImageView plusPic = findViewById(R.id.add_pic);
        plusPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusWhat=1;

                // initialising intent
                Intent intent = new Intent();

                // setting type to select to be image
                intent.setType("image/*");

                // allowing multiple image to be selected
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra("where", "picture");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
            }
        });

        ImageView plusBackground = findViewById(R.id.add_back);
        plusBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusWhat=2;

                // initialising intent
                Intent intent = new Intent();

                // setting type to select to be image
                intent.setType("image/*");

                // allowing multiple image to be selected
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra("where", "picture");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
            }
        });
    }
    int PICK_IMAGE_MULTIPLE = 1;


    public void onClick1side(View view){
        if(isFirstClicked){
            isFirstClicked=false;
        }else{
            isFirstClicked=true;
        }
        ImageView arrow1 = findViewById(R.id.arrow_pic);
        GridView tv = findViewById(R.id.picture_grid);
        changeButton(arrow1, isFirstClicked, tv);
    }
    public void onClick2side(View view){
        if(isSecondClicked){
            isSecondClicked=false;
        }else{
            isSecondClicked=true;
        }
        ImageView arrow2 = findViewById(R.id.arrow_fr);
        ListView tv = findViewById(R.id.backg_list);
        changeButton(arrow2, isSecondClicked, tv);
    }
    public void changeButton(ImageView iv, boolean isClick, View tv){
        if(isClick){
            iv.setImageResource(R.drawable.up_arrow_unshow);
            tv.setVisibility(View.VISIBLE);
        }else{
            iv.setImageResource(R.drawable.down_arrow_show);
            tv.setVisibility(View.GONE);
        }
    }
    @Override
    protected void onStop(){
        super.onStop();
        SharedPreferences.Editor editor = sharPrefs.edit();
        editor.putBoolean("firstInSample", isFirstClicked);
        editor.putBoolean("secondInSample", isSecondClicked);
        editor.apply();
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();
                int cout = data.getClipData().getItemCount();
                for (int i = 0; i < cout; i++) {
                    Uri imageurl = data.getClipData().getItemAt(i).getUri();
                    ImageView imvi = new ImageView(this);
                    imvi.setImageURI(imageurl);
                    imvi.setTag(imageurl);
                    if(plusWhat==1){
                        pics.add(imvi);
                    }else if(plusWhat==2){
                        backs.add(imvi);
                    }
                }
            } else {
                Uri imageurl = data.getData();
                ImageView imvi = new ImageView(this);
                imvi.setImageURI(imageurl);
                imvi.setTag(imageurl);
                if(plusWhat==1){
                    pics.add(imvi);
                }else if(plusWhat==2){
                    backs.add(imvi);
                }
            }
            if(plusWhat==1){
                GridView gv1 = findViewById(R.id.picture_grid);
                gv1.invalidateViews();
            }else if(plusWhat==2){
                ListView lv1 = findViewById(R.id.backg_list);
                lv1.invalidateViews();
            }

        }
    }


}

