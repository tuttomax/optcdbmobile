package com.optc.optcdbmobile.data.tasks;

public interface AsyncTaskListner<T> {
    void onPreExecute();

    void onPostExecute(T value);
}
