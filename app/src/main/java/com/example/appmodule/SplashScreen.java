package com.example.appmodule;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);
            changeScreen();
        }

        public void changeScreen() {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
}
