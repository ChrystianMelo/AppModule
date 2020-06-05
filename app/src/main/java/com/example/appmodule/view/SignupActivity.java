package com.example.appmodule.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appmodule.utils.FieldVerification;
import com.example.appmodule.R;
import com.example.appmodule.data.account.SignupServices;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;


public class SignupActivity extends AppCompatActivity {
    Button btnNext;
    TextInputEditText mail, pass, name;
    TextInputLayout mailLayout,passLayout, nameLayout;

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

        setButtonFunct();
    }

    public void setButtonFunct(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                FieldVerification verify = new FieldVerification();
                verify.cleanErrorMessages(mailLayout);
                verify.cleanErrorMessages(nameLayout);
                verify.cleanErrorMessages(passLayout);
                verify.setErrorMessages(mail,mailLayout,verify.verificationMail(getMail()));
                verify.setErrorMessages(name,nameLayout,verify.verificationName(getName()));
                verify.setErrorMessages(pass,passLayout,verify.verificationPass(getPass()));
                if( verify.isVerified(passLayout) &&
                    verify.isVerified(mailLayout) &&
                    verify.isVerified(nameLayout)){
                        createAcc();
                    }
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void createAcc() {
        SignupServices services = new SignupServices(getName(), getMail(), getPass());
        services.setData();
        if (services.getSigned()) {
            startActivity(new Intent(this, ProfileActivity.class));
        }
    }

}
