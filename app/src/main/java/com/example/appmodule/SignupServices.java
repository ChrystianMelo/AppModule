package com.example.appmodule;

import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

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
        setLink("https://lh3.googleusercontent.com/-Mj_p8uemhx0/AAAAAAAAAAI/AAAAAAAAAAA/AMZuucmtfvnixYvFPtRbroJwRGa3TTOLyg/photo.jpg");
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
