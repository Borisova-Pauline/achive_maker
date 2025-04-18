package com.example.achive_maker;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;

import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    public void handleUriPermission(Uri uri, ContentResolver cr){
        int flag = Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION;
        cr.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
    }
}
