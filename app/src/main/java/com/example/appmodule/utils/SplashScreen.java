package com.example.appmodule.utils;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmodule.R;
import com.example.appmodule.repositories.account.ProfileRepository;
import com.example.appmodule.view.LoginActivity;
import com.example.appmodule.view.ProfileActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);

            changeScreen();
        }

        public void changeScreen() {
            ProfileRepository repo = new ProfileRepository();
            if(repo.isUserLogged()) startActivity(new Intent(this, ProfileActivity.class));
            else                    startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
}
