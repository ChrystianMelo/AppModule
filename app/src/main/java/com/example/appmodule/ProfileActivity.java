package com.example.appmodule;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    TextView nametxt,emailtxt;
    ImageView img;
    Button upd;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nametxt = findViewById(R.id.txt_profile_name);
        img = findViewById(R.id.img_profile_picture);
        emailtxt = findViewById(R.id.txt_profile_mail);
        upd = findViewById(R.id.btn_profile_update);

        getData();

        setButtonFunctions();

    }

    public void openDialog(){
        DialogProfile dialog  = new DialogProfile();
        dialog.show(getSupportFragmentManager(), "Dialog Profile");
    }

    public void setButtonFunctions(){
        upd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getData() {
        nametxt.postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                new DownLoadImageTask(img).execute(Objects.requireNonNull(Objects.requireNonNull(user).getPhotoUrl()).toString());
                nametxt.append(Objects.requireNonNull(user).getDisplayName());
                emailtxt.append(Objects.requireNonNull(user).getEmail());
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
