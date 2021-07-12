package com.example.the_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;

import com.example.the_project.databinding.ActivityLoginBinding;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    FirebaseAuth auth;
    static boolean flag = false;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        binding.IdLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.createUserWithEmailAndPassword(binding.IdEmail.getText().toString(),
                        binding.IdPassword.getText().toString());
            }
        });

        binding.IdPassword.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if(event.getAction() == MotionEvent.ACTION_UP) {
                if(event.getRawX() >= (binding.IdPassword.getRight() -
                        binding.IdPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                    if(flag) {
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
    }
}