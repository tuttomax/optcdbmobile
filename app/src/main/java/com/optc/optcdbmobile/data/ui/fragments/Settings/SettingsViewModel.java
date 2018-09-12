package com.optc.optcdbmobile.data.ui.fragments.Settings;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

public class SettingsViewModel extends AndroidViewModel {

    private MutableLiveData<String> databaseVersion;
    private MutableLiveData<String> appVersion;

    private MutableLiveData<Boolean> updateAvailable;

    public SettingsViewModel(@NonNull Application application) {
        super(application);

        appVersion = new MutableLiveData<>();
        databaseVersion = new MutableLiveData<>();
        updateAvailable = new MutableLiveData<>();

    }

    public MutableLiveData<String> getAppVersion() {
        return appVersion;
    }

    public LiveData<String> getDatabaseVersion() {
        return databaseVersion;
    }

    public void setDatabaseVersion(String newVersion) {
        this.databaseVersion.setValue(newVersion);
    }

    public LiveData<Boolean> getUpdateAvailable() {
        return updateAvailable;
    }

    public void setUpdateAvailable(Boolean value) {
        this.updateAvailable.setValue(value);
    }

    public void setAppVersion(String appVersion) {
        this.appVersion.setValue(appVersion);
    }
}
