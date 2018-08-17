package com.optc.optcdbmobile.data.tasks;

import android.os.AsyncTask;

import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.database.OPTCDatabase;
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

public class ParallelBuildingDatabaseAsyncTask extends AsyncTask<Void, Object, Integer> {

    private AsyncTaskListner<Integer> listener;
    private OPTCDatabase database;
    private TaskDelegate delegate;

    public ParallelBuildingDatabaseAsyncTask(AsyncTaskListner<Integer> listener) {
        this.listener = listener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (listener != null) {
            listener.onPreExecute();
        }


    }

    @Override
    protected Integer doInBackground(Void... voids) {

        try {
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
        if (listener != null) {
            listener.onPostExecute(returnedValue);
        }
    }

    public void setDatabase(OPTCDatabase database) {
        this.database = database;
    }

    public void setDelegate(TaskDelegate delegate) {
        this.delegate = delegate;
    }
}
