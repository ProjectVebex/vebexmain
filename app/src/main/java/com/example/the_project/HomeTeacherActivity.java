package com.example.the_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.the_project.databinding.ActivityHomeBinding;
import com.example.the_project.databinding.ActivityHomeTeacherBinding;
import com.google.firebase.auth.FirebaseAuth;

public class HomeTeacherActivity extends AppCompatActivity {
    ActivityHomeTeacherBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityHomeTeacherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.chatbtn.setOnClickListener(v->{
            startActivity(new Intent(HomeTeacherActivity.this,ChattingTeacherActivity.class));
        });

        binding.notesbtn.setOnClickListener(v->{
            startActivity(new Intent(HomeTeacherActivity.this,NotesTeacherActivity.class));
        });

        binding.noticebtn.setOnClickListener(v->{
            startActivity(new Intent(HomeTeacherActivity.this,NoticeTeacherActivity.class));
        });

        binding.assignbtn.setOnClickListener(v->{
            startActivity(new Intent(HomeTeacherActivity.this,AssignmentTeacherActivity.class));
        });

        binding.quizbtn.setOnClickListener(v->{
            startActivity(new Intent(HomeTeacherActivity.this,QuizTeacherActivity.class));
        });

        binding.performbtn.setOnClickListener(v->{
            startActivity(new Intent(HomeTeacherActivity.this,PerformanceTeacherActivity.class));
        });
    }
}