package com.optc.optcdbmobile.data;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.Service;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;
import com.optc.optcdbmobile.BuildConfig;
import com.optc.optcdbmobile.data.tasks.AsyncTaskListener;
import com.optc.optcdbmobile.data.tasks.CheckAppLatestRelease;

import java.io.File;
import java.util.List;

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
                                File apkLocation = new File(downloads, Constants.APP.APP_INSTALL_NAME);

                                Intent installApk = null;
                                Uri uriLocation = null;
                                if (Build.VERSION.SDK_INT > 23) {

                                    uriLocation = FileProvider.getUriForFile(context, "com.optc.optcdbmobile.fileprovider", apkLocation);
                                    installApk = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                                } else {
                                    uriLocation = Uri.fromFile(apkLocation);
                                    installApk = new Intent(Intent.ACTION_VIEW);

                                }
                                installApk.setDataAndType(uriLocation, Constants.APP.MIME_TYPE_APK);

                                installApk.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                                context.startActivity(installApk);

                                context.unregisterReceiver(this);
                            }
                        }
                    }
                }, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            }
        });

    }

    public void CheckUpdate() {
        final Boolean updateAvailable;
        CheckAppLatestRelease checkTask = new CheckAppLatestRelease();
        checkTask.setListener(new AsyncTaskListener<UpdateInfo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void onPostExecute(UpdateInfo info) {
                if (info == null) {
                    Toast.makeText(mContext, "Error downloading app update", Toast.LENGTH_LONG).show();
                } else {

                    final Long newVersion = Long.parseLong(info.getTag());
                    final boolean updateAvailable = BuildConfig.VERSION_CODE < newVersion;
                    if (updateAvailable) {

                        Toast.makeText(mContext, "Downloading app update...", Toast.LENGTH_LONG).show();

                        if (info.getAssets().size() > 0) {

                            new AlertDialog.Builder(mContext).setTitle("App update available")
                                    .setMessage(info.getMessage()).setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();

                            UpdateInfo.Asset asset = info.getAssets().get(0);


                            Uri downloadUri = Uri.parse(asset.getUrl());
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

                        } else {
                            Log.e(UpdateManager.class.getSimpleName(), "assets = 0. error parsing json!?");
                        }

                    }
                }
            }
        });
        checkTask.execute();
    }

    public static class UpdateInfo {
        private List<Asset> assets;
        @SerializedName("body")
        private String message;
        @SerializedName("tag_name")
        private String tag;

        public UpdateInfo(List<Asset> assets, String message, String tag) {
            this.assets = assets;
            this.message = message;
            this.tag = tag;
        }

        public List<Asset> getAssets() {
            return assets;
        }

        public String getMessage() {
            return message;
        }

        public String getTag() {
            return tag;
        }

        public static class Asset {
            private String name;
            @SerializedName("browser_download_url")
            private String url;

            public Asset(String name, String url) {
                this.name = name;
                this.url = url;
            }

            public String getName() {
                return name;
            }

            public String getUrl() {
                return url;
            }
        }
    }
}
