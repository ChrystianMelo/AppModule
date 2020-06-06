package com.example.appmodule.repositories.account;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.appmodule.view.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupRepository extends AppCompatActivity {
    String name, pass, mail, link;

    String DEFAULT_PROFILE_PIC = "https://firebasestorage.googleapis.com/v0/b/appmodule-f6c22.appspot.com/o/images%2Fprofile.jpg?alt=media&token=ec92b4b3-18d6-4185-a1e0-8739577b2832";

    public String getLink() {return link;}
    public void setLink(String link) {this.link = link; }

    public String getMail() {return mail;}
    public void setMail(String mail) {this.mail = mail;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getPass() { return pass;}
    public void setPass(String pass) {this.pass = pass;}

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public SignupRepository(String name, String mail, String pass){
        setName(name);
        setMail(mail);
        setPass(pass);
        setLink(DEFAULT_PROFILE_PIC);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public MutableLiveData setData(){
        final MutableLiveData response =  new MutableLiveData();
        mAuth.createUserWithEmailAndPassword(getMail(), getPass())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseUser user = mAuth .getCurrentUser();
                            if (user !=  null) {
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(getName())
                                        .setPhotoUri(Uri.parse(getLink()))//get from firebase
                                        .build();
                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) response.setValue(true);
                                                else response.setValue(false);
                                            }
                                        });
                            }
                        }
                    }
                });
        return response;
    }
}
