package com.optc.optcdbmobile.data.tasks;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.UpdateManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CheckAppLatestRelease extends AsyncTask<Void, Void, UpdateManager.UpdateInfo> {


    private long currentRelease;
    private AsyncTaskListener<UpdateManager.UpdateInfo> listener;

    public void setListener(AsyncTaskListener<UpdateManager.UpdateInfo> listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        if (listener != null) {
            listener.onPreExecute();
        }
    }

    @Override
    protected UpdateManager.UpdateInfo doInBackground(Void... voids) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(Constants.APP.GITHUB_LATEST_RELEASE).openConnection();
            connection.setRequestMethod("GET");


            return new Gson().fromJson(new BufferedReader(new InputStreamReader(connection.getInputStream())), UpdateManager.UpdateInfo.class);

        } catch (MalformedURLException mex) {
            mex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(UpdateManager.UpdateInfo result) {

        if (listener != null) {
            listener.onPostExecute(result);
        }
    }
}
