package com.c.faizpay.Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.c.faizpay.R;

public class LoadingPrimer {

    Activity activity;
    AlertDialog dialog;

    public LoadingPrimer(Activity activity) {
        this.activity = activity;
    }

    public void startDialogLoading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loadingprimer, null));
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

    }

    public void startDialogLoadingCancleAble() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loadingprimer, null));
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

    }

    public void dismissDialog() {
        dialog.dismiss();

    }
}
