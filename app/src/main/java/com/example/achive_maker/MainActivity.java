package com.example.achive_maker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    public void createAch(View view){
        MyAdapter myAd = new MyAdapter(this, achives);
        ListView lv = findViewById(R.id.achs);
        achives.add(new Achivement());
        lv.setAdapter(myAd);
    }
}