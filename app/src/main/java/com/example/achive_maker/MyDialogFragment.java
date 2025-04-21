package com.example.achive_maker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class MyDialogFragment extends DialogFragment {
    Context context;
    MyDialogFragment(Context context){
        this.context = context;
    }
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Выйти?")
                .setMessage("Если вы выйдете, всё созданное в конструторе не сохранится")
                .setPositiveButton("Выйти", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                        if(context instanceof Activity){
                            ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    }
                })
                .setNegativeButton("Остаться", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .create();
    }
}
