package com.optc.optcdbmobile.data.database.threading;
//TODO Implement onerror

public interface TaskCallback {
    void onStateChanged(int i, int state);

    void onOperationChanged(int i, String opeartion);

    void onCurrentChanged(int i, int current);

    void onMaxChanged(int i, int max);

    void onError(Exception ex);
}
