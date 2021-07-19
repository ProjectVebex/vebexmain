package com.example.the_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.the_project.adapter.NotesTeacherAdapter;
import com.example.the_project.databinding.ActivityNotesTeacherBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotesTeacherActivity extends AppCompatActivity {
    ActivityNotesTeacherBinding binding;
    ArrayList<String> listSubjectName;
    ArrayList<String> listSubjectId;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotesTeacherBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());

        NotesTeacherAdapter adapter = new NotesTeacherAdapter(listSubjectName, listSubjectId ,this);
        binding.IdNotesTeacherRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.IdNotesTeacherRecyclerView.setLayoutManager(layoutManager);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        database.getReference().child("Teacher").child(auth.getCurrentUser().getUid()).child("Class").
                orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listSubjectName = new ArrayList<String>();
                listSubjectId = new ArrayList<String>();
                for(DataSnapshot snap : snapshot.getChildren()) {
                    String subjectName = snap.getValue(String.class);
                    String subjectId = snap.getKey();
                    listSubjectName.add(subjectName);
                    listSubjectId.add(subjectId);
                }
                adapter.notifyDataSetChanged();
            }

            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}