/*
 * Copyright 2018 alessandro
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.optc.optcdbmobile.data.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.CrashReporter;
import com.optc.optcdbmobile.data.UpdateManager;
import com.optc.optcdbmobile.data.database.OPTCDatabaseRepository;
import com.optc.optcdbmobile.data.tasks.AsyncTaskContext;
import com.optc.optcdbmobile.data.ui.fragments.CharacterTable.CharacterTableFragment;
import com.optc.optcdbmobile.data.ui.fragments.Settings.SettingsFragment;


public class MainActivity extends AppCompatActivity implements AsyncTaskContext {

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0 && permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (grantResults.length != 0) {
                int result = grantResults[0];
                if (result == PackageManager.PERMISSION_GRANTED) {

                    new UpdateManager(this, this).CheckUpdate();

                } else {
                    Snackbar.make(getView(), "Can't download update on Download Public folder", BaseTransientBottomBar.LENGTH_LONG)
                            .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                                @Override
                                public void onDismissed(Snackbar transientBottomBar, int event) {
                                    Snackbar.make(getView(), "You have to download and install it manually", Snackbar.LENGTH_LONG).show();
                                }
                            })
                            .show();
                }
            }
            //else is cancelled
        }
    }

    public static final String CHARACTER_TABLE_FRAGMENT = CharacterTableFragment.class.getSimpleName();
    private static final String SETTINGS_TAG_FRAGMENT = SettingsFragment.class.getSimpleName();

    private OPTCDatabaseRepository databaseRepository;
    private DrawerLayout drawer;
    private NavigationView nav;

    private Boolean isFirstLaunch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CrashReporter.install(this);

        setContentView(R.layout.activity_main);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            new UpdateManager(this, this).CheckUpdate();
        }




        isFirstLaunch = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Constants.Settings.pref_first_launch, true);


        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_home);

        drawer = findViewById(R.id.drawer_layout);
        nav = findViewById(R.id.nav_view);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawer.closeDrawer(Gravity.START);

                if (item.getItemId() == R.id.nav_menu_character_table) {
                    initFragment(CHARACTER_TABLE_FRAGMENT);

                } else if (item.getItemId() == R.id.nav_menu_damage_calculator) {

                } else if (item.getItemId() == R.id.nav_menu_drop_table) {

                } else if (item.getItemId() == R.id.nav_menu_evolution_helper) {

                } else if (item.getItemId() == R.id.nav_menu_probability_calculator) {

                } else if (item.getItemId() == R.id.nav_menu_slot_planner) {

                } else if (item.getItemId() == R.id.nav_menu_settings) {
                    initFragment(SETTINGS_TAG_FRAGMENT);
                }
                return true;
            }
        });

        databaseRepository = OPTCDatabaseRepository.getInstance(getApplication());
        if (isFirstLaunch) {
            databaseRepository.BuildAndSetLatestCommit(this);
            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(Constants.Settings.pref_first_launch, false).apply();
        } else {
            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(Constants.Settings.pref_check_done_key, false).commit();
            databaseRepository.CheckVersion(this);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();

        nav.setCheckedItem(R.id.nav_menu_character_table);
        initFragment(CHARACTER_TABLE_FRAGMENT);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawer.openDrawer(Gravity.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View getView() {
        return findViewById(android.R.id.content);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onBackPressed() {
        //TODO Set checked inside drawer on back key press
        if (drawer.isDrawerOpen(Gravity.START)) drawer.closeDrawer(Gravity.START);

        FragmentManager.BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1);
        if (CHARACTER_TABLE_FRAGMENT.equals(entry.getName())) {
            finish();
        } else {
            initFragment(CHARACTER_TABLE_FRAGMENT);
        }
    }


    private void initFragment(String TAG) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragmentToInit = null;

        if (TAG.equals(CHARACTER_TABLE_FRAGMENT)) {
            fragmentToInit = new CharacterTableFragment();
        } else if (TAG.equals(SETTINGS_TAG_FRAGMENT)) {
            fragmentToInit = new SettingsFragment();
        }

        transaction
                .replace(R.id.content_frame, fragmentToInit)
                .addToBackStack(TAG)
                .commit();

    }


}


