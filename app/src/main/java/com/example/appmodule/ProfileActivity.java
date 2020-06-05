package com.example.appmodule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity implements DialogProfile.DialogProfileListener {

    TextView nametxt,emailtxt;
    ImageView imgview;
    Button infoupd;
    ImageButton imgupd;

    FirebaseUser user;

    public static final int PICK_IMAGE = 1;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nametxt = findViewById(R.id.txt_profile_name);
        imgview = findViewById(R.id.img_profile_picture);
        emailtxt = findViewById(R.id.txt_profile_mail);
        infoupd = findViewById(R.id.btn_profile_update);
        imgupd = findViewById(R.id.imgbtn_profile_updImg);

        user = FirebaseAuth.getInstance().getCurrentUser();

        getData();

        setButtonFunctions();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void applyTexts(String username, String usermail, String userpass) {
        setData(username, usermail, userpass);
        //refresh();
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setData(final String name, String mail, String pass){
        final boolean[] updated = new boolean[1];
        user.updatePassword(pass)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            updated[0] = true;
                        }
                    }
                });
        Objects.requireNonNull(user).updatePassword(pass);
        user.updateEmail(mail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            updated[0] = true;
                        }
                    }
                });
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            updated[0] = true;
                        }
                    }
                });
    }
    public void openDialog(){
        DialogProfile dialog  = new DialogProfile();
        dialog.show(getSupportFragmentManager(), "Dialog Profile");
    }

    public void setButtonFunctions(){
        infoupd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        imgupd.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("IntentReset")
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                @SuppressLint("IntentReset") Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
            }
        });
}
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getData() {
        nametxt.postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                new DownLoadImageTask(imgview).execute(Objects.requireNonNull(Objects.requireNonNull(user).getPhotoUrl()).toString());
                nametxt.setText(Objects.requireNonNull(user).getDisplayName());
                emailtxt.setText(Objects.requireNonNull(user).getEmail());
            }
        }, 1000);
    }
    private static class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        @SuppressLint("StaticFieldLeak")
        ImageView imageView;

        DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }
}
