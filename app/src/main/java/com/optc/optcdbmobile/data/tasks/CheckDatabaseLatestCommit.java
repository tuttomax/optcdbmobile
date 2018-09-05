package com.optc.optcdbmobile.data.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.optc.optcdbmobile.data.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CheckDatabaseLatestCommit extends AsyncTask<Void, Void, CheckDatabaseLatestCommit.CommitInfo> {
    private AsyncTaskListener<CommitInfo> listener;

    public void setListener(AsyncTaskListener<CommitInfo> listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        if (listener != null) {
            listener.onPreExecute();
        }
    }

    @Override
    protected CommitInfo doInBackground(Void... voids) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(Constants.Database.GITHUB_LATEST_COMMITS).openConnection();
            connection.setRequestMethod("GET");
            Gson gson = new Gson();
            JsonArray array = gson.fromJson(new BufferedReader(new InputStreamReader(connection.getInputStream())), JsonArray.class);
            if (array != null && array.size() > 0) {
                return gson.fromJson(array.get(0), CommitInfo.class);
            } else {
                Log.e(CheckDatabaseLatestCommit.class.getSimpleName(), "No recent commit");
            }
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
    protected void onPostExecute(CommitInfo commit) {
        if (listener != null) {
            listener.onPostExecute(commit);
        }
    }

    public static class CommitInfo {
        private String sha;
        private Commit commit;

        public CommitInfo(String sha, Commit commit) {
            this.sha = sha;
            this.commit = commit;
        }

        public String getSha() {
            return sha;
        }

        public Commit getCommit() {
            return commit;
        }

        public static class Commit {
            private String message;
            private Committer committer;

            public Commit(String message, Committer committer) {
                this.message = message;
                this.committer = committer;
            }

            public String getMessage() {
                return message;
            }

            public Committer getCommitter() {
                return committer;
            }

            public static class Committer {
                private String date;

                public Committer(String date) {
                    this.date = date;
                }

                public String getDate() {
                    return date;
                }
            }
        }

    }
}
