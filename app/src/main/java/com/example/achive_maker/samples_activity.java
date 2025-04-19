package com.example.achive_maker;

import static com.example.achive_maker.MainActivity.SHAR_PREFS_NAME;

import android.app.Activity;
import android.content.ClipData;
import android.content.ContentResolver;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class samples_activity extends AppCompatActivity {
    SharedPreferences sharPrefs;
    private boolean isFirstClicked = false;
    private boolean isSecondClicked = false;
    ArrayList<ImageView> pics = new ArrayList<>();
    ArrayList<String> picsURI = new ArrayList<>();
    ArrayList<ImageView> backs = new ArrayList<>();
    ArrayList<String> backsURI = new ArrayList<>();
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


        Uri firstPicURI = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.drawable.unknow_pic);
        Uri firstBackURI = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.drawable.backg_sample);
        loadPic();
        loadBack();
        if(pics.isEmpty()){
            ImageView ivPic = new ImageView(this);
            ivPic.setImageURI(firstPicURI);
            ivPic.setTag(firstPicURI);
            pics.add(ivPic);
            picsURI.add(firstPicURI.toString());
        }
        if(backs.isEmpty()){
            ImageView ivBack = new ImageView(this);
            ivBack.setImageURI(firstBackURI);
            ivBack.setTag(firstBackURI);
            backs.add(ivBack);
            backsURI.add(firstBackURI.toString());
        }




        AdapterPic myAdPic = new AdapterPic(this, pics);
        tv1.setAdapter(myAdPic);

        AdapterBackgr myAdBack = new AdapterBackgr(this, backs);
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
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
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
        View tv = findViewById(R.id.picture_grid);
        changeButton(arrow1, isFirstClicked, tv);


        changeHeightPics(findViewById(R.id.picture_grid), pics, 340);
    }
    public void onClick2side(View view){
        if(isSecondClicked){
            isSecondClicked=false;
        }else{
            isSecondClicked=true;
        }
        ImageView arrow2 = findViewById(R.id.arrow_fr);
        View tv = findViewById(R.id.backg_list);
        changeButton(arrow2, isSecondClicked, tv);

        changeHeightBack(findViewById(R.id.backg_list), backs, 300);
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
        savePic();
        saveBack();
        SharedPreferences.Editor editor = sharPrefs.edit();
        editor.putBoolean("firstInSample", isFirstClicked);
        editor.putBoolean("secondInSample", isSecondClicked);
        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        changeHeightPics(findViewById(R.id.picture_grid), pics, 340);
        changeHeightBack(findViewById(R.id.backg_list), backs, 300);
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
                        picsURI.add(imageurl.toString());
                    }else if(plusWhat==2){
                        backs.add(imvi);
                        backsURI.add(imageurl.toString());
                    }
                    try{
                        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);
                        ContentResolver resolver = getContentResolver();
                        viewModel.handleUriPermission(imageurl, resolver);
                    }catch(Exception ex){
                        Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Uri imageurl = data.getData();
                ImageView imvi = new ImageView(this);
                imvi.setImageURI(imageurl);
                imvi.setTag(imageurl);
                if(plusWhat==1){
                    pics.add(imvi);
                    //picsURI.add(imageurl.toString());
                }else if(plusWhat==2){
                    backs.add(imvi);
                    //backsURI.add(imageurl.toString());
                }
                try{
                    MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);
                    ContentResolver resolver = getContentResolver();
                    viewModel.handleUriPermission(imageurl, resolver);
                }catch(Exception ex){
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            if(plusWhat==1){
                GridView gv1 = findViewById(R.id.picture_grid);
                gv1.invalidateViews();
                changeHeightPics(findViewById(R.id.picture_grid), pics, 340);
            }else if(plusWhat==2){
                ListView lv1 = findViewById(R.id.backg_list);
                lv1.invalidateViews();
                changeHeightBack(findViewById(R.id.backg_list), backs, 300);
            }
        }
    }
    public void changeHeightPics(GridView ll, ArrayList<ImageView> ivList, int prefOneH){
        int height=120;
        if((ivList.size())%3>0){
            height=((ivList.size()/3)+1)*prefOneH;
        }else{
            height=ivList.size()/3*prefOneH;
        }
        ViewGroup.LayoutParams params = ll.getLayoutParams();
        params.height = height;
        ll.setLayoutParams(params);
    }
    public void changeHeightBack(ListView ll, ArrayList<ImageView> ivList, int prefOneH){
        int height=ivList.size()*prefOneH;
        ViewGroup.LayoutParams params = ll.getLayoutParams();
        params.height = height;
        ll.setLayoutParams(params);
    }

    private final static String FILE_NAME_PIC = "content_pic.txt";
    private final static String FILE_NAME_BACK = "content_back.txt";
    public void savePic() {
        FileOutputStream fos = null;
        String textSave = "";
        for (int i = 0; i < picsURI.size(); i++) {
            if (i < picsURI.size() - 1) {
                textSave = textSave + picsURI.get(i) + "\n104\n";
            } else {
                textSave = textSave + picsURI.get(i);
            }
        }
        try {
            fos = openFileOutput(FILE_NAME_PIC, MODE_PRIVATE);
            fos.write(textSave.getBytes());
        } catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void saveBack() {
        FileOutputStream fos = null;
        String textSave = "";
        for (int i = 0; i < backsURI.size(); i++) {
            if (i < backsURI.size() - 1) {
                textSave = textSave + backsURI.get(i) + "\n104\n";
            } else {
                textSave = textSave + backsURI.get(i);
            }
        }
        try {
            fos = openFileOutput(FILE_NAME_BACK, MODE_PRIVATE);
            fos.write(textSave.getBytes());
        } catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void loadPic(){
        FileInputStream fin = null;
        try {
            fin = openFileInput(FILE_NAME_PIC);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            String[]temp=text.split("\n104\n");
            for(int i=0;i<temp.length;i++){
                picsURI.add(temp[i]);
                ImageView iv = new ImageView(this);
                iv.setImageURI(Uri.parse(picsURI.get(i)));
                iv.setTag(Uri.parse(picsURI.get(i)));
                pics.add(iv);
            }
        }
        catch(Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            try {
                if (fin != null)
                    fin.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void loadBack(){
        FileInputStream fin = null;
        try {
            fin = openFileInput(FILE_NAME_BACK);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            String[]temp=text.split("\n104\n");
            for(int i=0;i<temp.length;i++){
                backsURI.add(temp[i]);
                ImageView iv = new ImageView(this);
                iv.setImageURI(Uri.parse(backsURI.get(i)));
                iv.setTag(Uri.parse(backsURI.get(i)));
                backs.add(iv);
            }
        }
        catch(Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            try {
                if (fin != null)
                    fin.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}