package com.example.appmodule;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;


public class SignupActivity extends AppCompatActivity {
    Button btnNext;
    TextInputEditText mail, pass, name;
    TextInputLayout mailLayout,passLayout, nameLayout;
    private FirebaseAuth mAuth;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String getMail() {
        return Objects.requireNonNull(mail.getText()).toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String getPass() {
        return Objects.requireNonNull(pass.getText()).toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String getName() {
        return Objects.requireNonNull(name.getText()).toString();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        btnNext = findViewById(R.id.btn_signup_next);
        mailLayout =  findViewById(R.id.til_signup_mailLayout);
        passLayout =  findViewById(R.id.til_signup_passLayout);
        nameLayout =  findViewById(R.id.til_signup_nameLayout);
        name    =  findViewById(R.id.tiet_signup_name);
        mail    =  findViewById(R.id.tiet_signup_mail);
        pass    =  findViewById(R.id.tiet_signup_pass);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        setButtonFunct();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void updateUI(FirebaseUser user){

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(getName())
                .setPhotoUri(Uri.parse("https://lh3.googleusercontent.com/-Mj_p8uemhx0/AAAAAAAAAAI/AAAAAAAAAAA/AMZuucmtfvnixYvFPtRbroJwRGa3TTOLyg/photo.jpg"))
                .build();

        Objects.requireNonNull(user).updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i("hereeeeeee", "User profile updated.");
                        }else{
                            Log.i("hereeeeeee", "User profile nott updated.");
                        }
                    }
                });
    }
    public void setButtonFunct(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                setOnDB();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void setOnDB(){
        final Intent intent = new Intent(this, ProfileActivity.class);
        mAuth.createUserWithEmailAndPassword(getMail(), getPass())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
}
