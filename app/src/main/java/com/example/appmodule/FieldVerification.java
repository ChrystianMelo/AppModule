package com.example.appmodule;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class FieldVerification extends AppCompatActivity {

    public int verificationPass(String field){
        if (field.isEmpty()) return 0;
        if (field.length()<8) return -1;
        return 1;
    }
    public int verificationName(String field){
        if (field.isEmpty()) return 0;
        if (field.length()<8) return -1;
        return 1;
    }

    public int verificationMail(String field){
        if (field.isEmpty()) return 0;
        if ((field.indexOf('.') == -1 ||
                field.indexOf('@') == -1) ||
                field.length() < 2)
            return -1;
        return 1;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setErrorMessages(TextInputEditText txt, TextInputLayout field, int vf) {
        if (vf == 1) {
            field.setError(null);
        } else if (vf == -1) {
            field.setError("Incorrect Format");
        } else if (vf == 0) {
            field.setError("Campo vazio");
        }
    }

    public boolean isVerified(TextInputLayout field) {
        return field.getError() == null ;
    }

    public void cleanErrorMessages(TextInputLayout field) {
        field.setError(null);
    }
}
