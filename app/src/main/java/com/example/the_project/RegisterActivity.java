package com.example.the_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.the_project.databinding.ActivityRegisterBinding;
import com.example.the_project.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        binding.SignInText.setOnClickListener(v ->{
            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        });

        binding.IdRegisterButton.setOnClickListener(v -> {
            if(binding.IdREmail.getText().toString().equals("")) {
                binding.IdREmail.setError("Enter Email");
                return;
            }
            if(binding.IdRName.getText().toString().equals("")) {
                binding.IdRName.setError("Enter Name");
                return;
            }
            if(binding.IdRPassword.getText().toString().equals("")) {
                binding.IdRPassword.setError("Enter password");
                return;
            }
            if(binding.IdRPassword.getText().toString().equals(binding.IdRConfirmPassword.getText().toString())){
                binding.IdRConfirmPassword.setError("Password not Matched");
                return;
            }
            if(!(checkEmail() && checkPassword())){
                return;
            }
            auth.createUserWithEmailAndPassword(
                    binding.IdREmail.getText().toString(),binding.IdRPassword.getText().toString()).
                    addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            User user = new User(binding.IdRName.getText().toString(),binding.IdREmail.getText().toString(),
                                    binding.IdRPassword.getText().toString(),"");
                            String uid = task.getResult().getUser().getUid();
                            user.setUid(uid);
                            database.getReference().child("User").child(uid).setValue(user);
                            Toast.makeText(RegisterActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    protected  boolean checkEmail() {
        StringBuilder email = new StringBuilder(binding.IdREmail.getText().toString());
        int index = email.indexOf("@");
        if(index == -1) {
            binding.IdREmail.setError("Enter valid email");
            return false;
        }
        email.substring(index+1);
        if(!email.toString().equals("walchandsangli.ac.in"))
        {
            binding.IdREmail.setError("Enter valid email");
            return false;
        }
        return true;
    }

    protected  boolean checkPassword() {
        String password = binding.IdRPassword.getText().toString();
        int iPasswordScore = 0;

        if( password.length() < 8 ) {
            binding.IdRPassword.setError("must be greater than 8 digits");
            return false;
        }
        else
            iPasswordScore += 2;

        //if it contains one digit, add 2 to total score
        if( password.matches("(?=.*[0-9]).*") )
            iPasswordScore += 2;

        //if it contains one lower case letter, add 2 to total score
        if( password.matches("(?=.*[a-z]).*") )
            iPasswordScore += 2;

        //if it contains one upper case letter, add 2 to total score
        if( password.matches("(?=.*[A-Z]).*") )
            iPasswordScore += 2;

        //if it contains one special character, add 2 to total score
        if( password.matches("(?=.*[~!@#$%^&*()_-]).*") )
            iPasswordScore += 2;

        if(iPasswordScore < 10)
        {
            binding.IdRPassword.setError("must contain aA-zZ, 0-9, ~!@#$%^&*()_-");
            return  false;
        }
        return  true;
    }
}