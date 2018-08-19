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

package com.optc.optcdbmobile.data.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.database.OPTCDatabaseRepository;
import com.optc.optcdbmobile.data.tasks.AsyncTaskContext;
import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.CharacterTableFragment;
import com.optc.optcdbmobile.data.ui.activities.fragments.Settings.SettingsFragment;


// TODO Avoid activity in async task

public class MainActivity extends AppCompatActivity implements AsyncTaskContext {
    private static final String SETTINGS_TAG_FRAGMENT = "SETTINGS_TAG";

    private OPTCDatabaseRepository databaseRepository;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());

        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView nav = findViewById(R.id.nav_view);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawer.closeDrawer(Gravity.START);

                if (item.getItemId() == R.id.nav_menu_character_table) {
                    getSupportFragmentManager()
                            .beginTransaction().replace(R.id.content_frame, new CharacterTableFragment())
                            .commit();

                } else if (item.getItemId() == R.id.nav_menu_damage_calculator) {

                } else if (item.getItemId() == R.id.nav_menu_drop_table) {

                } else if (item.getItemId() == R.id.nav_menu_evolution_helper) {

                } else if (item.getItemId() == R.id.nav_menu_probability_calculator) {

                } else if (item.getItemId() == R.id.nav_menu_slot_planner) {

                } else if (item.getItemId() == R.id.nav_menu_settings) {
                    getSupportFragmentManager()
                            .beginTransaction().replace(R.id.content_frame, new SettingsFragment(), SETTINGS_TAG_FRAGMENT)
                            .commit();

                }
                return true;
            }
        });


        databaseRepository = OPTCDatabaseRepository.getInstance(getApplication());
    }

    @Override
    protected void onResume() {
        super.onResume();
        databaseRepository.CheckVersion(this);
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
        Fragment settings_fragment = getSupportFragmentManager().findFragmentByTag(SETTINGS_TAG_FRAGMENT);
        if (settings_fragment != null && settings_fragment.isVisible()) {
            if (drawer.isDrawerOpen(Gravity.START)) {
                drawer.closeDrawer(Gravity.START);
            } else {
                drawer.openDrawer(Gravity.START);
            }
        } else {
            super.onBackPressed();
        }
    }


}


