package com.optc.optcdbmobile.data;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.preference.PreferenceManager;

public class WelcomeMessage {

    public static void Show(final Activity activity) {
        boolean isFirstLaunch = PreferenceManager.getDefaultSharedPreferences(activity).getBoolean(Constants.Settings.pref_first_launch, false);

        if (!isFirstLaunch) return;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Welcome")
                .setMessage("There was a technical update inside database.\nPlease delete data of your app\nand rebuild database.\nThanks.")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
