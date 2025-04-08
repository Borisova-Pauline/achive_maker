package com.example.achive_maker;

import static com.example.achive_maker.MainActivity.SHAR_PREFS_NAME;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class samples_activity extends AppCompatActivity {
    SharedPreferences sharPrefs;
    private boolean isFirstClicked = false;
    private boolean isSecondClicked = false;

    public void toMainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    public void toLookDelMenu(View view){
        Intent intent = new Intent(this, LookOrDel.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_samples);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sharPrefs = getSharedPreferences(SHAR_PREFS_NAME, MODE_PRIVATE);
        isFirstClicked=sharPrefs.getBoolean("firstInSample", false);
        isSecondClicked=sharPrefs.getBoolean("secondInSample", false);

        View arrow1 = findViewById(R.id.arrow_pic);
        LinearLayout tv1 = findViewById(R.id.pic_list);
        changeButton((ImageView) arrow1, isFirstClicked, tv1);

        View arrow2 = findViewById(R.id.arrow_fr);
        LinearLayout tv2 = findViewById(R.id.fr_list);
        changeButton((ImageView) arrow2, isSecondClicked, tv2);
    }
    public void onClick1side(View view){
        if(isFirstClicked){
            isFirstClicked=false;
        }else{
            isFirstClicked=true;
        }
        ImageView arrow1 = findViewById(R.id.arrow_pic);
        LinearLayout tv = findViewById(R.id.pic_list);
        changeButton(arrow1, isFirstClicked, tv);
    }
    public void onClick2side(View view){
        if(isSecondClicked){
            isSecondClicked=false;
        }else{
            isSecondClicked=true;
        }
        ImageView arrow2 = findViewById(R.id.arrow_fr);
        LinearLayout tv = findViewById(R.id.fr_list);
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
}