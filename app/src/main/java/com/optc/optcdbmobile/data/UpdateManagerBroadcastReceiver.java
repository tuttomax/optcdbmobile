package com.optc.optcdbmobile.data;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

public class UpdateManagerBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {

            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            String[] columns = {MediaStore.MediaColumns.DATA};

            Long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Uri uri = downloadManager.getUriForDownloadedFile(id);

            Cursor newCursor = context.getContentResolver().query(uri, columns, null, null, null);
            newCursor.moveToFirst();
            String location = newCursor.getString(newCursor.getColumnIndex(columns[0]));

            Intent installApk = new Intent(Intent.ACTION_VIEW);
            installApk.setDataAndType(Uri.fromFile(new File(location)), Constants.APP.MIME_TYPE_APK);
            installApk.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            context.startActivity(installApk);

            context.unregisterReceiver(this);
        }
    }
}
