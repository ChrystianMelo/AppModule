package com.example.appmodule.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;

import com.example.appmodule.utils.DialogProfile;
import com.example.appmodule.R;
import com.example.appmodule.repositories.account.ProfileRepository;
import com.example.appmodule.utils.NavBottom;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

public class ProfileActivity extends NavBottom implements DialogProfile.DialogProfileListener {

    TextView nametxt,emailtxt;
    ImageView imgview;
    ImageButton infoupd, imgupd, logout;
    LinearLayout load;
    ConstraintLayout content;

    public static final int PICK_IMAGE = 1;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nametxt = findViewById(R.id.txt_profile_name);
        imgview = findViewById(R.id.img_profile_picture);
        emailtxt = findViewById(R.id.txt_profile_mail);
        infoupd = findViewById(R.id.imgbtn_profile_update);
        imgupd = findViewById(R.id.imgbtn_profile_updImg);
        logout = findViewById(R.id.imgbtn_profile_logout);
        content = findViewById(R.id.cl_profile_screen);
        load = findViewById(R.id.ll_profile_loading);

        getData();
        setButtonFunctions();

    }

    public void changeLoadingAnnimation(LinearLayout loading, ConstraintLayout screen) {
        float loadBak = loading.getAlpha();
        loading.setAlpha(screen.getAlpha());
        screen.setAlpha(loadBak);
    }
    void openErrorDialog(String msg){
        final AlertDialog.Builder alert = new AlertDialog.Builder(ProfileActivity.this);
        alert.setTitle("Error");
        alert.setPositiveButton("OK",null);
        alert.setMessage(msg);
        alert.show();
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
        logout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                ProfileRepository services = new ProfileRepository();
                services.signOut();
                goHome();
            }
        });
        imgupd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                getImg();
            }
        });
    }

    void goHome(){
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
    void refresh(){
        startActivity(new Intent(this, ProfileActivity.class));
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getData() {
        final ProfileRepository services = new ProfileRepository();
        final String[] data = services.getBasicUserInfo();
        nametxt.postDelayed(new Runnable() {
            @Override
            public void run() {
                new ProfileActivity.DownLoadImageTask(imgview).execute(data[0]);
                nametxt.setText(data[1]);
                emailtxt.setText(data[2]);
            }
        }, 1000);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void applyTexts(String username) {
        ProfileRepository services = new ProfileRepository();
        changeLoadingAnnimation(load,content);
        services.setData(username).observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                if(o.equals(true)) {
                    refresh();
                }else{
                    changeLoadingAnnimation(load,content);
                    openErrorDialog("You new name is not availble\nTry again later!");
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                Bitmap bitmap;
                try {
                    changeLoadingAnnimation(load,content);
                    ProfileRepository services = new ProfileRepository();
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Objects.requireNonNull(data.getData())));
                    services.setLocalAsProfilePic(bitmap).observe(this, new Observer() {
                        @Override
                        public void onChanged(Object o) {
                            if(o.equals(true)){
                                refresh();
                            }else {
                                changeLoadingAnnimation(load,content);
                                openErrorDialog("Your change was not possible\nTry again later!");
                            }
                        }
                    });
                } catch (FileNotFoundException e) { e.printStackTrace(); }
            }
        }
    }

    @SuppressLint("IntentReset")
    public void getImg(){
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        @SuppressLint("IntentReset") Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);
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
