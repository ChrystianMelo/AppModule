package com.example.appmodule.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.appmodule.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class DialogProfile extends AppCompatDialogFragment {

    private TextInputEditText name;
    private TextInputLayout nameLayout;
    private DialogProfileListener listener;

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
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialog_profile, null);

        nameLayout = view.findViewById(R.id.til_dialog_nameLayout);
        name    =  view.findViewById(R.id.tiet_dialog_name);

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
                        FieldVerification verify = new FieldVerification();
                        verify.cleanErrorMessages(nameLayout);
                        verify.setErrorMessages(name,nameLayout,verify.verificationName(getName()));
                        if(verify.isVerified(nameLayout)) {
                            String username = getName();
                            listener.applyTexts(username);
                        }
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
        void applyTexts(String username);
    }
}
