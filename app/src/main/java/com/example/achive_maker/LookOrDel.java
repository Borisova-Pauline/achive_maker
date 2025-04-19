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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class LookOrDel extends AppCompatActivity {

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
        String example = intent.getStringExtra("picture");
        ImageView iv = findViewById(R.id.samp_look_del);
        try{
            iv.setImageURI(Uri.parse(example));
        }catch(Exception ex){
            ex.getMessage();
        }

        //iv.setImageURI(Uri.parse(intent.getStringExtra("picture")));

    }

    @Override
    protected void onStart(){
        super.onStart();
        Intent intent = getIntent();
        if(!isRemove){
        switch(intent.getStringExtra("pic_or_back")){
            case "pic":
                loadPic();
                break;
            case "back":
                loadBack();
                break;
            default:
                Toast.makeText(this, "Какая-то ошибка", Toast.LENGTH_LONG).show();
        }}
    }

    public void toSampleMenu(View view){
        Intent intent = new Intent(this, samples_activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    public void deleteThis(View view){
        Intent intent = getIntent();
        String example = intent.getStringExtra("picture");
        if(example.equals("android.resource://" + this.getPackageName() + "/" + R.drawable.unknow_pic)
                ||example.equals("android.resource://" + this.getPackageName() + "/" + R.drawable.backg_sample)){
            Toast.makeText(this, "Ты не можешь удалить это, это дефолтный шаблон", Toast.LENGTH_LONG).show();
        }else{
            switch(intent.getStringExtra("pic_or_back")){
                case "pic":
                    picsURI.remove(example);
                    Toast.makeText(this, "Картинка удалена", Toast.LENGTH_LONG).show();
                    savePic();
                    break;
                case "back":
                    backsURI.remove(example);
                    Toast.makeText(this, "Фон удалён", Toast.LENGTH_LONG).show();
                    saveBack();
                    break;
                default:
                    Toast.makeText(this, "Какая-то ошибка", Toast.LENGTH_LONG).show();
            }
            Intent intent1 = new Intent(this, samples_activity.class);
            startActivity(intent1);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

    }


    private final static String FILE_NAME_PIC = "content_pic.txt";
    private final static String FILE_NAME_BACK = "content_back.txt";
    ArrayList<String> backsURI = new ArrayList<>();
    ArrayList<String> picsURI = new ArrayList<>();
    boolean isRemove = false;
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