package com.example.appmodule;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class DialogProfile extends AppCompatDialogFragment {

    TextInputEditText mail, pass, name;
    TextInputLayout mailLayout,passLayout, nameLayout;
    DialogProfileListener listener;

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_profile, null);

        mailLayout = view.findViewById(R.id.til_dialog_mailLayout);
        passLayout =  view.findViewById(R.id.til_dialog_passLayout);
        nameLayout =  view.findViewById(R.id.til_dialog_nameLayout);
        name    =  view.findViewById(R.id.tiet_dialog_name);
        mail    =  view.findViewById(R.id.tiet_dialog_mail);
        pass    =  view.findViewById(R.id.tiet_dialog_pass);

        builder.setView(view)
                .setTitle("Update Info")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String username = getName();
                        String usermail = getMail();
                        String userpass = getPass();
                        listener.applyTexts(username,usermail,userpass);
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (DialogProfileListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+
                    "must implement DialogProfileListener");
        }

    }

    public interface DialogProfileListener{
        void applyTexts(String username,String usermail, String userpass);
    }
}
