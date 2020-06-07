package com.example.appmodule.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;

import com.example.appmodule.utils.FieldVerification;
import com.example.appmodule.R;
import com.example.appmodule.repositories.account.LoginRepository;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    Button btnNext;
    TextInputEditText mail, pass;
    TextInputLayout mailLayout,passLayout;
    LinearLayout load;
    ConstraintLayout layout;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String getMail() {
        return Objects.requireNonNull(mail.getText()).toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String getPass() {
        return Objects.requireNonNull(pass.getText()).toString();
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
        layout = findViewById(R.id.cl_login_screen);
        load = findViewById(R.id.ll_login_loading);

        setButtonFunct();
    }

    public void changeLoadingAnnimation(LinearLayout loading, ConstraintLayout screen) {
        float loadBak = loading.getAlpha();
        loading.setAlpha(screen.getAlpha());
        screen.setAlpha(loadBak);
    }
    void openErrorDialog(String msg){
        final AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
        alert.setTitle("Error");
        alert.setPositiveButton("OK",null);
        alert.setMessage(msg);
        alert.show();
    }


    public void setButtonFunct(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                FieldVerification verify = new FieldVerification();
                verify.cleanErrorMessages(mailLayout);
                verify.cleanErrorMessages(passLayout);
                verify.setErrorMessages(mail,mailLayout,verify.verificationMail(getMail()));
                verify.setErrorMessages(pass,passLayout,verify.verificationPass(getPass()));
                if(verify.isVerified(passLayout) && verify.isVerified(mailLayout)) {
                        checkData();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void checkData(){
        LoginRepository lservices = new LoginRepository();
        changeLoadingAnnimation(load,layout);
        lservices.signIn(getMail(), getPass()).observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                if(o.equals(true)) {
                    goHome();
                }else{
                    changeLoadingAnnimation(load,layout);
                    openErrorDialog("Verify your email address and try again.");
                }
            }
        });
    }

    void goHome(){
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    public void Create(View v){
        startActivity(new Intent(this, SignupActivity.class));
        finish();
    }
}
