package com.optc.optcdbmobile.data.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.MainViewModel;
import com.optc.optcdbmobile.data.database.OPTCDatabaseRepository;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private OPTCDatabaseRepository databaseRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);


        databaseRepository = OPTCDatabaseRepository.getInstance(getApplication());

    }

    @Override
    protected void onResume() {
        super.onResume();

        databaseRepository.CheckVersion(findViewById(R.id.mainActivityRoot));
    }
}


