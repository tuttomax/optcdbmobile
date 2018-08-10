package com.optc.optcdbmobile.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import com.optc.optcdbmobile.data.database.OPTCDatabaseRepository;

public class MainViewModel extends AndroidViewModel {
    private OPTCDatabaseRepository repository;

    public MainViewModel(Application application) {
        super(application);
        repository = new OPTCDatabaseRepository(application);
    }


}
