package com.example.achive_maker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class EditAchive extends AppCompatActivity {
    private final static String FILE_NAME_PIC = "content_pic.txt";
    private final static String FILE_NAME_BACK = "content_back.txt";
    private final static String FILE_NAME_ACH = "content_achievement.txt";
    ArrayList<ImageView> pics = new ArrayList<>();
    ArrayList<String> picsURI = new ArrayList<>();
    ArrayList<ImageView> backs = new ArrayList<>();
    ArrayList<String> backsURI = new ArrayList<>();
    ArrayList<Achivement> achivements = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_achive);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Uri firstPicURI = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.drawable.unknow_pic);
        Uri firstBackURI = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.drawable.backg_sample);
        loadPic();
        loadBack();
        loadAchive();
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



        Intent intent = getIntent();
        pos = intent.getIntExtra("position", 0);
        iv1 = findViewById(R.id.picture_ed);
        iv1.setImageURI(Uri.parse(achivements.get(pos).picURI));
        iv1.setTag(Uri.parse(achivements.get(pos).picURI));
        iv2= findViewById(R.id.background_ed);
        iv2.setImageURI(Uri.parse(achivements.get(pos).backURI));
        iv2.setTag(Uri.parse(achivements.get(pos).backURI));
        et1 = findViewById(R.id.naming_ed);
        et1.setText(achivements.get(pos).textT);
        et2 = findViewById(R.id.descrip_et_edit);
        et2.setText(achivements.get(pos).description);


        GridView tv1 = findViewById(R.id.pic_samp_gv_edit);
        AdapterPicOnCreate myAdPic = new AdapterPicOnCreate(this, pics, iv1);
        tv1.setAdapter(myAdPic);

        ListView tv2 = findViewById(R.id.back_samp_lv_edit);
        AdapterBackOnCreate myAdBack = new AdapterBackOnCreate(this, backs, iv2);
        tv2.setAdapter(myAdBack);

        /*ImageView iv1 = findViewById(R.id.picture_ed);
        iv1.setTag("android.resource://" + this.getPackageName() + "/" + R.drawable.unknow_pic);
        ImageView iv2= findViewById(R.id.background_ed);
        iv2.setTag("android.resource://" + this.getPackageName() + "/" + R.drawable.backg_sample);*/
    }
    public void toMainMenu(View view){
        MyDialogFragment dialog = new MyDialogFragment(this, "create_edit");
        dialog.show(getSupportFragmentManager(), "custom");
    }

    public void description(View view){
        EditText et = findViewById(R.id.descrip_et_edit);
        et.setVisibility(View.VISIBLE);
        GridView gv = findViewById(R.id.pic_samp_gv_edit);
        gv.setVisibility(View.GONE);
        ListView lv = findViewById(R.id.back_samp_lv_edit);
        lv.setVisibility(View.GONE);
    }
    public void picSamp(View view){
        EditText et = findViewById(R.id.descrip_et_edit);
        et.setVisibility(View.GONE);
        GridView gv = findViewById(R.id.pic_samp_gv_edit);
        gv.setVisibility(View.VISIBLE);
        ListView lv = findViewById(R.id.back_samp_lv_edit);
        lv.setVisibility(View.GONE);
    }
    public void backSamp(View view){
        EditText et = findViewById(R.id.descrip_et_edit);
        et.setVisibility(View.GONE);
        GridView gv = findViewById(R.id.pic_samp_gv_edit);
        gv.setVisibility(View.GONE);
        ListView lv = findViewById(R.id.back_samp_lv_edit);
        lv.setVisibility(View.VISIBLE);
    }
    int pos = 0;
    ImageView iv1;
    String picURI;
    ImageView iv2;
    String backURI;
    EditText et1;
    String text;
    EditText et2;
    String desc;
    //Achivement ach = new Achivement(achivements.size(), picURI, text, backURI, desc);
    public void doAchive(View view){
        picURI = iv1.getTag().toString();
        backURI = iv2.getTag().toString();
        text = et1.getText().toString();
        desc = et2.getText().toString();
        achivements.get(pos).picURI = picURI;
        achivements.get(pos).textT = text;
        achivements.get(pos).backURI = backURI;
        achivements.get(pos).description = desc;
        saveAchive();

        Intent intent1 = new Intent(this, MainActivity.class);
        startActivity(intent1);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
    Context context = this;
    public void deleteAch(View view){
        DialogInterface.OnClickListener ocl = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                achivements.remove(pos);
                saveAchive();
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        };


        MyDialogFragment dialog = new MyDialogFragment(this, "delete", ocl);
        dialog.show(getSupportFragmentManager(), "custom");
    }

    public void saveAchive(){
        FileOutputStream fos = null;
        String textSave = "";
        for (int i = 0; i < achivements.size(); i++) {
            if (i < achivements.size() - 1) {
                textSave = textSave + achivements.get(i).picURI + "\n104\n";
                textSave = textSave + achivements.get(i).textT + "\n104\n";
                textSave = textSave + achivements.get(i).backURI + "\n104\n";
                textSave = textSave + achivements.get(i).description + "\n104401104\n";
            } else {
                textSave = textSave + achivements.get(i).picURI + "\n104\n";
                textSave = textSave + achivements.get(i).textT + "\n104\n";
                textSave = textSave + achivements.get(i).backURI + "\n104\n";
                textSave = textSave + achivements.get(i).description;
            }
        }
        try {
            fos = openFileOutput(FILE_NAME_ACH, MODE_PRIVATE);
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

    public void loadAchive(){
        FileInputStream fin = null;
        try {
            fin = openFileInput(FILE_NAME_ACH);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            String[]temp=text.split("\n104401104\n");
            for(int i=0;i<temp.length;i++){
                String[] tempObj = temp[i].split("\n104\n");
                Achivement a = new Achivement(i, tempObj[0], tempObj[1], tempObj[2], tempObj[3]);
                achivements.add(a);
            }
        }
        catch(Exception ex) {
            Toast.makeText(this, ex.getMessage()+"\nload", Toast.LENGTH_SHORT).show();
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