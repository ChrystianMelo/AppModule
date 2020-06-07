package com.example.appmodule.utils;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmodule.repositories.account.ProfileRepository;
import com.example.appmodule.view.HomeActivity;
import com.example.appmodule.view.LoginActivity;
import com.example.appmodule.view.PostActivity;
import com.example.appmodule.view.ProfileActivity;
import com.example.appmodule.view.SearchActivity;

public class NavBottom extends AppCompatActivity {
    public void goProf(View view) {
        ProfileRepository repo = new ProfileRepository();
        if(repo.isUserLogged())
            startActivity(new Intent(view.getContext(), ProfileActivity.class));
        else
            startActivity(new Intent(view.getContext(), LoginActivity.class));
        finish();
    }
    public void goHome(View view) {
        startActivity(new Intent(view.getContext(), HomeActivity.class));
        finish();
    }
    public void goSearch(View view) {
        startActivity(new Intent(view.getContext(), SearchActivity.class));
        finish();
    }
    public void uploadImg(View view) {
        startActivity(new Intent(view.getContext(), PostActivity.class));
        finish();
    }

}
