package com.example.achive_maker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LookOrDel extends AppCompatActivity {
    int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_look_or_del);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();

        ImageView iv = findViewById(R.id.samp_look_del);
        try{
            iv.setImageURI(Uri.parse(intent.getStringExtra("picture")));
        }catch(Exception ex){
            ex.getMessage();
        }
        //iv.setImageURI(Uri.parse(intent.getStringExtra("picture")));

    }
    public void toSampleMenu(View view){
        Intent intent = new Intent(this, samples_activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    public void deleteThis(View view){
        Toast.makeText(this, "Ты не можешь удалить это", Toast.LENGTH_LONG).show();
    }
}