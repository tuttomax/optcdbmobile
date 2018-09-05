package com.optc.optcdbmobile.data.tasks;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.google.gson.internal.bind.util.ISO8601Utils;
import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.database.OPTCDatabaseRepository;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;

public class CheckDatabaseLatestCommitTaskListener implements AsyncTaskListener<CheckDatabaseLatestCommit.CommitInfo> {
    private AsyncTaskContext context;
    private String currentCommitString;
    private boolean autoDownload;

    public CheckDatabaseLatestCommitTaskListener(AsyncTaskContext context) {
        this.context = context;
    }

    @Override
    public void onPreExecute() {
        currentCommitString = PreferenceManager.getDefaultSharedPreferences(context.getContext()).getString(Constants.Database.latest_commit_key, "");
        autoDownload = PreferenceManager.getDefaultSharedPreferences(context.getContext()).getBoolean(Constants.Settings.pref_auto_download_key, false);

    }

    @Override
    public void onPostExecute(final CheckDatabaseLatestCommit.CommitInfo value) {
        try {


            Date currentCommit = currentCommitString.isEmpty() ? null : ISO8601Utils.parse(currentCommitString, new ParsePosition(0));
            Date newCommit = ISO8601Utils.parse(value.getCommit().getCommitter().getDate(), new ParsePosition(0));

            final boolean updateAvailable = (currentCommit == null || currentCommit.before(newCommit));

            PreferenceManager.getDefaultSharedPreferences(context.getContext()).edit()
                    .putBoolean(Constants.Settings.pref_update_available, updateAvailable)
                    .apply();


            if (updateAvailable) {

                if (autoDownload) {
                    Snackbar.make(context.getView(), "Updating database...", Snackbar.LENGTH_SHORT).show();
                    OPTCDatabaseRepository.getInstance(context.getView().getContext()).BuildDatabase(context);

                    PreferenceManager.getDefaultSharedPreferences(context.getContext())
                            .edit()
                            .putString(Constants.Settings.pref_database_version_key, value.getSha())
                            .apply();

                    PreferenceManager.getDefaultSharedPreferences(context.getContext())
                            .edit().putString(Constants.Database.latest_commit_key, value.getCommit().getCommitter().getDate())
                            .apply();

                } else {
                    Snackbar.make(context.getView(), "New database version available", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.WHITE)
                            .setAction("CHECK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    new AlertDialog.Builder(context.getContext())
                                            .setTitle("Database update - What's new?")
                                            .setMessage(value.getCommit().getMessage())
                                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            })
                                            .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    OPTCDatabaseRepository.getInstance(context.getView().getContext()).BuildDatabase(context);
                                                    PreferenceManager.getDefaultSharedPreferences(context.getContext())
                                                            .edit()
                                                            .putString(Constants.Settings.pref_database_version_key, value.getSha())
                                                            .apply();

                                                    PreferenceManager.getDefaultSharedPreferences(context.getContext())
                                                            .edit().putString(Constants.Database.latest_commit_key, value.getCommit().getCommitter().getDate())
                                                            .apply();

                                                }
                                            })
                                            .show();
                                }
                            }).show();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {

            PreferenceManager.getDefaultSharedPreferences(context.getContext()).edit()
                    .putBoolean(Constants.Settings.pref_update_available, false)
                    .apply();

            PreferenceManager.getDefaultSharedPreferences(context.getContext()).edit()
                    .putBoolean(Constants.Settings.pref_check_done_key, true)
                    .apply();
        }
    }

}
