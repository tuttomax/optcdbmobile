package com.optc.optcdbmobile.data.tasks;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.database.OPTCDatabase;
import com.optc.optcdbmobile.data.database.threading.ListTaskDialog;
import com.optc.optcdbmobile.data.database.threading.Task;
import com.optc.optcdbmobile.data.database.threading.TaskCallback;
import com.optc.optcdbmobile.data.database.threading.TaskDelegate;
import com.optc.optcdbmobile.data.database.threading.tasks.CooldownsPopulateTask;
import com.optc.optcdbmobile.data.database.threading.tasks.DetailsPopulatorTask;
import com.optc.optcdbmobile.data.database.threading.tasks.DropsPopulateTask;
import com.optc.optcdbmobile.data.database.threading.tasks.EvolutionsPopulateTask;
import com.optc.optcdbmobile.data.database.threading.tasks.FamiliesPopulateTask;
import com.optc.optcdbmobile.data.database.threading.tasks.UnitsPopulateTask;
import com.optc.optcdbmobile.data.optcdb.API;

import java.lang.ref.WeakReference;

public class ParallelBuildingDatabaseAsyncTask extends AsyncTask<Void, Object, Integer> {

    WeakReference<Activity> refActivity;
    WeakReference<View> refView;

    public ParallelBuildingDatabaseAsyncTask(Activity activity) {
        refActivity = new WeakReference<>(activity);
        refView = new WeakReference<>(refActivity.get().findViewById(android.R.id.content));
    }


    TaskDelegate delegate;
    ListTaskDialog dialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ListTaskDialog.newInstance("Building database");
        delegate = dialog.getAdapterDelegate();
        dialog.show(((AppCompatActivity) refActivity.get()).getSupportFragmentManager(), ListTaskDialog.TAG);
    }

    @Override
    protected Integer doInBackground(Void... voids) {

        try {
            OPTCDatabase database = OPTCDatabase.getInstance(refActivity.get());
            database.clearAllTables();

            //Task with no FOREIGN KEY constraint
            Task unitsTask = new UnitsPopulateTask(database, (TaskCallback) delegate);
            Task dropsTask = new DropsPopulateTask(database, (TaskCallback) delegate);
            Task detailsTask = new DetailsPopulatorTask(database, (TaskCallback) delegate);

            delegate.addTasks(unitsTask, dropsTask, detailsTask);
            delegate.awaitTermination(80);

            //Task with FOREIGN KEY constraint
            Task evolutionsTask = new EvolutionsPopulateTask(database, (TaskCallback) delegate);
            Task familyTask = new FamiliesPopulateTask(database, (TaskCallback) delegate);
            Task cooldownsTask = new CooldownsPopulateTask(database, (TaskCallback) delegate);

            delegate.addTasks(evolutionsTask, familyTask, cooldownsTask);
            delegate.awaitTermination(20);

        } catch (Exception ex) {
            return -1;
        }

        return (Integer) API.getData(Constants.APIType.VERSION_TYPE);
    }

    @Override
    protected void onPostExecute(Integer returnedValue) {
        dialog.dismiss();
        if (returnedValue > 0) {
            PreferenceManager.getDefaultSharedPreferences(refActivity.get()).edit()
                    .putInt(Constants.Settings.pref_database_version_key, returnedValue).commit();
            Snackbar.make(refView.get(), "Database building complete", Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(refView.get(), "Error building database", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .setAction("REDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new ParallelBuildingDatabaseAsyncTask(refActivity.get()).execute();
                        }
                    }).show();
        }
    }
}
