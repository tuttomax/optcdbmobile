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

package com.optc.optcdbmobile.data.ui.activities.fragments.Settings;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.database.OPTCDatabaseRepository;
import com.optc.optcdbmobile.data.tasks.AsyncTaskContext;

import java.util.logging.Logger;

public class SettingsFragment extends PreferenceFragmentCompat implements AsyncTaskContext {
    private final static String TAG = SettingsFragment.class.getSimpleName();
    SettingsViewModel model;
    SharedPreferences.OnSharedPreferenceChangeListener listner = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            Logger.getLogger(TAG).info(key);

            if (key.equals(getString(R.string.pref_database_version_key))) {
                model.setDatabaseVersion(sharedPreferences.getInt(key, -1));
            } else if (key.equals(Constants.Settings.pref_update_available)) {
                model.setUpdateAvailable(sharedPreferences.getBoolean(key, false));
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(this).get(SettingsViewModel.class);

        model.getDatabaseVersion().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer newVersion) {
                findPreference(getString(R.string.pref_database_version_key)).setSummary(String.valueOf(newVersion));
            }
        });

        model.getUpdateAvailable().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean b) {
                if (b == null) throw new NullPointerException();
                String title = b ? "Update available" : "Rebuild database";
                int color = b ? R.color.infoColor : R.color.primaryTextColor;
                final SingleLinePreference rebuild_database = (SingleLinePreference) findPreference(getString(R.string.pref_rebuild_database_key));
                rebuild_database.setTitle(title);
                rebuild_database.setTitleColor(ResourcesCompat.getColor(getResources(), color, null));
            }
        });

    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        String key = preference.getKey();

        if (key.equals(getString(R.string.pref_open_source_key))) {
            startActivity(new Intent(getActivity(), OssLicensesMenuActivity.class));
            return true;
        }

        if (key.equals(getString(R.string.pref_rebuild_database_key))) {
            OPTCDatabaseRepository.getInstance(getActivity().getApplication()).BuildDatabase(SettingsFragment.this);
            return true;
        }

        return super.onPreferenceTreeClick(preference);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String rootKey) {
        PreferenceManager.setDefaultValues(getContext(), R.xml.pref_settings, false);
        setPreferencesFromResource(R.xml.pref_settings, rootKey);

        final SingleLinePreference databaseVersionPreference = (SingleLinePreference) findPreference(getString(R.string.pref_database_version_key));
        Integer currentVersion = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt(getString(R.string.pref_database_version_key), -1);
        databaseVersionPreference.setSummary(String.valueOf(currentVersion));
    }

    @Override
    public void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(listner);
    }

    @Override
    public void onStop() {
        PreferenceManager.getDefaultSharedPreferences(getActivity()).unregisterOnSharedPreferenceChangeListener(listner);
        super.onStop();
    }


    @Override
    public FragmentManager getSupportFragmentManager() {
        return getFragmentManager();
    }


}


