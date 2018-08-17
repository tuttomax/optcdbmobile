package com.optc.optcdbmobile.data.tasks;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;

public interface AsyncTaskContext {
    FragmentManager getSupportFragmentManager();

    View getView();

    Context getContext();
}
