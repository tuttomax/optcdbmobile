package com.optc.optcdbmobile.data.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.MainViewModel;
import com.optc.optcdbmobile.data.database.OPTCDatabaseRepository;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private OPTCDatabaseRepository databaseRepository;
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO Remove this - DEBUG ONLY
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());

        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView nav = findViewById(R.id.nav_view);


        Button nav_menu_settings = findViewById(R.id.nav_menu_settings);
        nav_menu_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });


        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawer.closeDrawers();

                if (item.getItemId() == R.id.nav_menu_character_table) {

                } else if (item.getItemId() == R.id.nav_menu_damage_calculator) {

                } else if (item.getItemId() == R.id.nav_menu_drop_table) {

                } else if (item.getItemId() == R.id.nav_menu_evolution_helper) {

                } else if (item.getItemId() == R.id.nav_menu_probability_calculator) {

                } else if (item.getItemId() == R.id.nav_menu_slot_planner) {

                }

                return true;
            }
        });


        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        databaseRepository = OPTCDatabaseRepository.getInstance(getApplication());
    }

    @Override
    protected void onResume() {
        super.onResume();
        View view = findViewById(android.R.id.content);
        if (view == null) throw new RuntimeException("Container view is null");

        databaseRepository.CheckVersion(view);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawer.openDrawer(Gravity.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


