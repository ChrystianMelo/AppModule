package com.example.appmodule.data.account;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class SignupServices extends AppCompatActivity {
    String name, pass, mail, link;
    boolean singed = false;

    public boolean getSigned() {return singed;}
    public void setSigned(boolean value) {this.singed = value; }

    public String getLink() {return link;}
    public void setLink(String link) {this.link = link; }

    public String getMail() {return mail;}
    public void setMail(String mail) {this.mail = mail;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getPass() { return pass;}
    public void setPass(String pass) {this.pass = pass;}

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public SignupServices(String name,String mail,String pass){
        setName(name);
        setMail(mail);
        setPass(pass);
        setLink("https://firebasestorage.googleapis.com/v0/b/appmodule-f6c22.appspot.com/o/images%2Fprofile.jpg?alt=media&token=ec92b4b3-18d6-4185-a1e0-8739577b2832");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setData(){
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
                                user.updateProfile(profileUpdates);
                            }
                        }
                    }
                });

        getData();
    }

    void compare(String name,String mail,String link){
        if(name.equals(getName()) &&
            mail.equals(getMail())&&
            link.equals(getLink()))
                setSigned(true);
    }

    public void getData(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            //boolean emailVerified = user.isEmailVerified();

            assert photoUrl != null;
            assert name != null;
            compare(name,email,photoUrl.getPath());
        }
    }



}
