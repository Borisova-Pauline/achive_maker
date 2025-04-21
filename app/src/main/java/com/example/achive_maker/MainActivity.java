package com.example.achive_maker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharPrefs;
    public static String SHAR_PREFS_NAME = "sharPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sharPrefs = getSharedPreferences(SHAR_PREFS_NAME, MODE_PRIVATE);

        loadAchive();

        MyAdapter myAd = new MyAdapter(this, achives);
        ListView lv = findViewById(R.id.achs);
        lv.setAdapter(myAd);
    }
    public void toSampleMenu(View view){
        SharedPreferences.Editor editor = sharPrefs.edit();
        editor.putBoolean("firstInSample", false);
        editor.putBoolean("secondInSample", false);
        editor.apply();


        Intent intent = new Intent(this, samples_activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    public void toCreateMenu(View view){
        Intent intent = new Intent(this, CreateAchive.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }



        /////////НЕ ТРОГАТЬ, ЭТО ПОКАЗ ОБЪЕКТОВ НА ЭКРАНЕ, ВЗЯТЫЙ ИЗ ИНТЕРНЕТА; НЕ ТРОГАТЬ И КЛАСС MyAdapter Я ХЗ КАК ТАМ ВСЁ РАБОТАЕТ!!!!!!!!
    ArrayList<Achivement> achives = new ArrayList<>();
    /*public void createAch(View view){
        MyAdapter myAd = new MyAdapter(this, achives);
        ListView lv = findViewById(R.id.achs);
        achives.add(new Achivement());
        lv.setAdapter(myAd);
    }*/

    private final static String FILE_NAME_ACH = "content_achievement.txt";
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
                achives.add(a);
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