package com.example.the_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;

import com.example.the_project.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    static boolean flag = false;
    FirebaseAuth auth;
    ProgressDialog progressDialog;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Login to your account");

        binding.IdLoginButton.setOnClickListener(v -> {
            progressDialog.show();
            if(binding.IdEmail.getText().toString().equals("")){
                binding.IdEmail.setError("Enter email");
                progressDialog.hide();
                return;
            }
            if(binding.IdPassword.getText().toString().equals("")){
                binding.IdPassword.setError("Enter Password");
                progressDialog.hide();
                return;
            }
            auth.signInWithEmailAndPassword(binding.IdEmail.getText().toString() ,
                    binding.IdPassword.getText().toString()).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                progressDialog.hide();
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            }
                        }
                    });
        });

        binding.IdPassword.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if(event.getAction() == MotionEvent.ACTION_UP) {
                if(event.getRawX() >= (binding.IdPassword.getRight() -
                        binding.IdPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                    if(!flag) {
                        binding.IdPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        binding.IdPassword.setCompoundDrawablesWithIntrinsicBounds(0,0,
                                R.drawable.ic_hide_password,0);
                    }
                    else {
                        binding.IdPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        binding.IdPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                                R.drawable.ic_show_password, 0);
                    }
                    flag = !flag;
                    return true;
                }
            }
            return false;
        });


        binding.createUserText.setOnClickListener(v ->{
           startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        });
    }
}