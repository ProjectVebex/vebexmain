package com.example.the_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

public class NotesActivity extends AppCompatActivity {

    DownloadManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
    }

    public void download(Uri uri) {
        manager= (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        manager.enqueue(request);

    }
}