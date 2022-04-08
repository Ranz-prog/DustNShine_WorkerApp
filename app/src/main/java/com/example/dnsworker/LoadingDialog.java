package com.example.dnsworker;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.dnsworker.Service.UserService;

public class LoadingDialog {

    Activity activity;
    AlertDialog alertDialog;

    public LoadingDialog(Activity myActivity){
        activity = myActivity;
    }



    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.progress_dialog_custom, null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void startloginLoading(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.progress_dialog_custom_login, null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void dismissDialog(){
        alertDialog.dismiss();
    }
}
