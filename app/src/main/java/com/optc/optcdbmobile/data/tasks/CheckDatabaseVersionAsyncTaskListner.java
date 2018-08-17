package com.optc.optcdbmobile.data.tasks;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.database.OPTCDatabaseRepository;

import static com.optc.optcdbmobile.data.Constants.DatabaseVerionTask.ACTION_AUTOMATIC_UPDATE;
import static com.optc.optcdbmobile.data.Constants.DatabaseVerionTask.ACTION_SHOW_UPDATE;

public class CheckDatabaseVersionAsyncTaskListner implements AsyncTaskListner<Byte> {

    private AsyncTaskContext context;

    public CheckDatabaseVersionAsyncTaskListner(AsyncTaskContext supporter) {
        context = supporter;
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onPostExecute(Byte action) {
        if (action != null) {
            switch (action) {
                case ACTION_SHOW_UPDATE:

                    Snackbar.make(context.getView(), "New database version available", Snackbar.LENGTH_LONG)
                            .setActionTextColor(context.getView().getResources().getColor(R.color.secondaryLightColor))
                            .setAction("UPDATE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //new BuildDatabaseAsyncTask(refView.get()).execute();
                                    OPTCDatabaseRepository.getInstance(context.getView().getContext()).BuildDatabase(context);
                                }
                            }).show();
                    break;
                case ACTION_AUTOMATIC_UPDATE:
                    Snackbar.make(context.getView(), "Updating database...", Snackbar.LENGTH_SHORT).show();
                    //new BuildDatabaseAsyncTask(refView.get()).execute();
                    OPTCDatabaseRepository.getInstance(context.getView().getContext()).BuildDatabase(context);
                    break;
            }
        }

    }
}