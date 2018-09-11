package com.optc.optcdbmobile.data.tasks;

import android.os.AsyncTask;

public abstract class BaseAsyncTask<A, B, C> extends AsyncTask<A, B, C> {
    private AsyncTaskListener<C> listener;

    public void setListener(AsyncTaskListener<C> listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        if (listener != null) {
            listener.onPreExecute();
        }
    }

    @Override
    protected void onPostExecute(C c) {
        if (listener != null) {
            listener.onPostExecute(c);
        }
    }
}
