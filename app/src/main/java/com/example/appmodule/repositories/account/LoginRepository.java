package com.example.appmodule.repositories.account;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.appmodule.view.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginRepository extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;

    public MutableLiveData signIn(String mail, String pass){
        final MutableLiveData response =  new MutableLiveData();
        mAuth.signInWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                            response.setValue(true);
                        else
                            response.setValue(false);
                    }
                });
        return response;
    }
}
