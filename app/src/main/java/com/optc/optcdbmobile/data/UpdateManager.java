package com.optc.optcdbmobile.data;

import android.app.DownloadManager;
import android.app.Service;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.optc.optcdbmobile.BuildConfig;
import com.optc.optcdbmobile.data.optcdb.API;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class UpdateManager {

    private final Context mContext;
    private DownloadManager downloadManager;
    private MutableLiveData<Long> idFile;

    public UpdateManager(final Context context, LifecycleOwner owner) {
        mContext = context;
        downloadManager = (DownloadManager) context.getSystemService(Service.DOWNLOAD_SERVICE);
        idFile = new MutableLiveData<>();
        idFile.observe(owner, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable final Long id) {
                context.registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                            Long receivedId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                            if (receivedId.equals(id)) {
                                File downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                                String location = new File(downloads, Constants.APP.APP_INSTALL_NAME).getPath();

                                Intent installApk = new Intent(Intent.ACTION_VIEW);
                                installApk.setDataAndType(Uri.fromFile(new File(location)), Constants.APP.MIME_TYPE_APK);
                                installApk.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                                context.startActivity(installApk);
                                context.unregisterReceiver(this);

                            }
                        }
                    }
                }, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            }
        });

    }


    public void CheckUpdate() throws ExecutionException, InterruptedException {
        Pair<Boolean, Integer> updateInfo = Executors.newSingleThreadExecutor().submit(new Callable<Pair<Boolean, Integer>>() {
            @Override
            public Pair<Boolean, Integer> call() {
                Integer currentVersion = BuildConfig.VERSION_CODE;
                //TODO Implement GITHUB Commit Observer
                Integer newVersion = Integer.valueOf(API.simple_download(Constants.APP.APP_VERSION_URL));
                return Pair.create(currentVersion < newVersion, newVersion);
            }
        }).get();

        if (updateInfo.first) {

            Log.d(UpdateManager.class.getSimpleName(), "App update available");

            Toast.makeText(mContext, "Downloading app update...", Toast.LENGTH_LONG).show();

            Uri downloadUri = Uri.parse(String.format(Constants.APP.APP_DOWNLOAD_URL, updateInfo.second));

            File downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File apk = new File(downloads, Constants.APP.APP_INSTALL_NAME);

            if (apk.exists()) {
                boolean isDeleted = apk.delete();
                Log.i(UpdateManager.class.getSimpleName(), "old APK deleted? " + String.valueOf(isDeleted));
            }

            DownloadManager.Request downloadRequest = new DownloadManager.Request(downloadUri)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, Constants.APP.APP_INSTALL_NAME)
                    .setMimeType(Constants.APP.MIME_TYPE_APK)
                    .setVisibleInDownloadsUi(true)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);

            idFile.setValue(downloadManager.enqueue(downloadRequest));
        }
    }
}
