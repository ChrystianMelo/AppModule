package com.example.appmodule.utils;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmodule.R;
import com.example.appmodule.view.LoginActivity;
import com.example.appmodule.view.ProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);
            
            mAuth = FirebaseAuth.getInstance();
            
            changeScreen();
        }

        public void changeScreen() {
            FirebaseUser user = mAuth.getCurrentUser();
            if(user == null) startActivity(new Intent(this, LoginActivity.class));
            else             startActivity(new Intent(this, ProfileActivity.class));
            finish();
        }
}