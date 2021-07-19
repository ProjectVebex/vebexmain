package com.example.the_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.the_project.adapter.NotesTeacherAdapter;
import com.example.the_project.adapter.NotesUploadAdapter;
import com.example.the_project.databinding.ActivityNotesUploadBinding;
import com.example.the_project.model.NotesDetails;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotesUploadActivity extends AppCompatActivity {
    ActivityNotesUploadBinding binding;
    ArrayList<NotesDetails> list;
    FirebaseDatabase database;
    FirebaseStorage storage;
    FirebaseAuth auth;
    String subjectName;
    String subjectId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotesUploadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        subjectName = intent.getStringExtra("SubjectName");
        subjectId = intent.getStringExtra("SubjectId");

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        NotesUploadAdapter adapter = new NotesUploadAdapter(list ,this);
        binding.IdNotesUploadRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.IdNotesUploadRecyclerView.setLayoutManager(layoutManager);

        //adapter.notifyDataSetChanged();
        database.getReference().child(subjectId).child(subjectName).child("2021").child("Notes").
                orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()) {
                    NotesDetails ns = snap.getValue(NotesDetails.class);
                    list.add(ns);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        binding.IdNotesUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 33);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData() != null && requestCode == 33) {
            Uri uri = data.getData();
            List<String> l= data.getData().getPathSegments();
            String fileName = l.get(l.size()-1);

            final StorageReference reference = storage.getReference().child(subjectName).child(subjectId)
                    .child("2021").child("Notes");
            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(NotesUploadActivity.this, "File uploaded successfully",
                            Toast.LENGTH_SHORT).show();
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Date date =  new Date();
                            NotesDetails ns = new NotesDetails(fileName, uri);
                            database.getReference().child(subjectName).child(subjectId)
                                    .child("2021").child("Notes").child(date.toString()).setValue(ns);
                            
                        }
                    });
                }
            });

        }
    }
}