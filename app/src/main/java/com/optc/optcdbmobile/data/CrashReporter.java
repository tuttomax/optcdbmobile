package com.optc.optcdbmobile.data;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;

public class CrashReporter {

    private static WeakReference<Context> refContext;

    public static void install(Context context) {
        refContext = new WeakReference<>(context);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
    }


    public static class ExceptionHandler implements Thread.UncaughtExceptionHandler {
        private static final String LOG_TAG = ExceptionHandler.class.getSimpleName();
        private final String LINE_SEPARATOR = "\n";

        public void uncaughtException(Thread thread, Throwable exception) {
            StringWriter stackTrace = new StringWriter();
            exception.printStackTrace(new PrintWriter(stackTrace));
            stackTrace.flush();


            Log.e(LOG_TAG, stackTrace.toString());


            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "optcdbmobile@gmail.com", null));
            intent.putExtra(Intent.EXTRA_SUBJECT, "optcdmbile - Crash Report");
            intent.putExtra(Intent.EXTRA_TEXT, stackTrace.toString());

            ContextCompat.startActivity(refContext.get(), Intent.createChooser(intent, "Send crash report..."), null);

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        }
    }
}
