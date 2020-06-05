package com.example.appmodule.data.account;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginServices extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;

    public boolean signIn(String mail, String pass){
        final boolean[] logged = new boolean[1];
        mAuth.signInWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                            logged[0] = true;
                    }
                });
        Log.i("chrys","Sign IN ->"+logged[0]);
        return logged[0];
    }
}
