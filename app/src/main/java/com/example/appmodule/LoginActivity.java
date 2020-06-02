package com.example.appmodule;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    Button btnNext;
    TextInputEditText mail, pass;
    TextInputLayout mailLayout,passLayout;
    private FirebaseAuth mAuth;


    public String getMail() {
        return mail.getText().toString();
    }

    public String getPass() {
        return pass.getText().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnNext = findViewById(R.id.btn_login_next);
        mailLayout =  findViewById(R.id.til_login_mailLayout);
        passLayout =  findViewById(R.id.til_login_passLayout);
        mail    =  findViewById(R.id.tiet_login_mail);
        pass    =  findViewById(R.id.tiet_login_pass);

        mAuth = FirebaseAuth.getInstance();

        setButtonFunct();
    }


    public void checkData(){
        final Intent intent = new Intent(this,HomeActivity.class);
        mAuth.signInWithEmailAndPassword(getMail(), getPass())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("heree", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("heree", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                        // ...
                    }
                });
    }
    public void setButtonFunct(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                cleanErrorMessages(mailLayout);
                cleanErrorMessages(passLayout);
                setErrorMessages(mail,mailLayout,verificationMail(getMail()));
                setErrorMessages(pass,passLayout,verificationPass(getPass()));
                if(verifyFields()) checkData();
            }
        });
    }

    public void Create(View v){
        startActivity(new Intent(this, CreateAccActivity.class));
    }

    public int verificationPass(String field){
        if (field.isEmpty()) return 0;
        if (field.length()<8) return -1;
        return 1;
    }
    public int verificationMail(String field){
        if (field.isEmpty()) return 0;
        if ((field.indexOf('.') == -1 ||
                field.indexOf('@') == -1) ||
                field.length() < 2)
            return -1;
        return 1;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setErrorMessages(TextInputEditText txt, TextInputLayout field, int vf) {
        if (vf == 1) {
            field.setError(null);
        } else if (vf == -1) {
            field.setError("Incorrect Format");
        } else if (vf == 0) {
            field.setError("Campo vazio");
        }
    }

    public boolean verifyFields() {
        return mailLayout.getError() == null &&
                passLayout.getError() == null;
    }

    public void cleanErrorMessages(TextInputLayout field) {
        field.setError(null);
    }
}
