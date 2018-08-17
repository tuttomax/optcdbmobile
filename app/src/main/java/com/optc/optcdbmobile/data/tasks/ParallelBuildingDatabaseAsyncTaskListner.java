package com.optc.optcdbmobile.data.tasks;

import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.database.OPTCDatabaseRepository;
import com.optc.optcdbmobile.data.database.threading.ListTaskDialog;
import com.optc.optcdbmobile.data.database.threading.TaskDelegate;

public class ParallelBuildingDatabaseAsyncTaskListner implements AsyncTaskListner<Integer> {
    private AsyncTaskContext context;
    private View view;

    private ListTaskDialog dialog;

    public ParallelBuildingDatabaseAsyncTaskListner(AsyncTaskContext supporter) {
        context = supporter;
        view = context.getView();
        dialog = ListTaskDialog.newInstance("Building database");
    }

    @Override
    public void onPreExecute() {
        dialog.show(context.getSupportFragmentManager(), ListTaskDialog.TAG);
    }

    @Override
    public void onPostExecute(Integer returnedValue) {
        dialog.dismiss();
        if (returnedValue > 0) {
            PreferenceManager.getDefaultSharedPreferences(context.getContext()).edit()
                    .putInt(Constants.Settings.pref_database_version_key, returnedValue).commit();
            Snackbar.make(view, "Database building complete", Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(view, "Error building database", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .setAction("REDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            OPTCDatabaseRepository.getInstance(view.getContext()).BuildDatabase(context);
                        }
                    }).show();
        }
    }

    public TaskDelegate getDelegate() {
        return dialog.getAdapter();
    }
}
