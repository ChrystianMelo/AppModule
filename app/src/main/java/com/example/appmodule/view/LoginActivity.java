package com.example.appmodule.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appmodule.utils.FieldVerification;
import com.example.appmodule.R;
import com.example.appmodule.data.account.LoginServices;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    Button btnNext;
    TextInputEditText mail, pass;
    TextInputLayout mailLayout,passLayout;


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

        setButtonFunct();
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
                if(verify.isVerified(passLayout) && verify.isVerified(mailLayout))
                    checkData();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void checkData(){
        LoginServices lservices = new LoginServices();
        if (lservices.signIn(getMail(), getPass()))
            startActivity(new Intent(this,HomeActivity.class));

    }

    public void Create(View v){
        startActivity(new Intent(this, SignupActivity.class));
    }
}
