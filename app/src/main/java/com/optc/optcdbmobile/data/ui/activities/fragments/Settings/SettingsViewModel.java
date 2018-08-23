package com.optc.optcdbmobile.data.ui.activities.fragments.Settings;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

public class SettingsViewModel extends AndroidViewModel {

    private MutableLiveData<Float> databaseVersion;
    private MutableLiveData<Integer> appVersion;

    private MutableLiveData<Boolean> updateAvailable;

    public SettingsViewModel(@NonNull Application application) {
        super(application);

        databaseVersion = new MutableLiveData<>();
        appVersion = new MutableLiveData<>();

        updateAvailable = new MutableLiveData<>();
    }

    public LiveData<Float> getDatabaseVersion() {
        return databaseVersion;
    }

    public void setDatabaseVersion(Float newVersion) {
        this.databaseVersion.setValue(newVersion);
    }

    public LiveData<Integer> getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(Integer newVersion) {
        this.appVersion.setValue(newVersion);
    }

    public LiveData<Boolean> getUpdateAvailable() {
        return updateAvailable;
    }

    public void setUpdateAvailable(Boolean value) {
        this.updateAvailable.setValue(value);
    }

}
