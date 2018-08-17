package com.optc.optcdbmobile.data.ui.activities.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.database.OPTCDatabaseRepository;
import com.optc.optcdbmobile.data.tasks.AsyncTaskContext;
import com.optc.optcdbmobile.data.ui.controls.settings.SingleLinePreference;

public class SettingsFragment extends PreferenceFragmentCompat implements AsyncTaskContext {
    SharedPreferences.OnSharedPreferenceChangeListener listner = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals(getString(R.string.pref_database_version_key))) {
                findPreference(key)
                        .setSummary(
                                PreferenceManager.getDefaultSharedPreferences(getActivity())
                                        .getInt(key, -1));
            }

        }
    };

    @Override
    public void onCreatePreferences(Bundle bundle, String rootKey) {
        PreferenceManager.setDefaultValues(getContext(), R.xml.pref_settings, false);
        setPreferencesFromResource(R.xml.pref_settings, rootKey);


        Integer currentVersion = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt(getString(R.string.pref_database_version_key), -1);


        final SingleLinePreference rebuild_database = (SingleLinePreference) findPreference(getString(R.string.pref_rebuild_database_key));
        final SingleLinePreference databaseVersionPreference = (SingleLinePreference) findPreference(getString(R.string.pref_database_version_key));

        rebuild_database.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                OPTCDatabaseRepository.getInstance(getActivity().getApplication()).BuildDatabase(SettingsFragment.this);
                return true;
            }
        });
        if (Constants.Settings.there_is_update) {
            rebuild_database.setTitle("Update available");
            int color = ResourcesCompat.getColor(getResources(), R.color.secondaryColor, null);
            rebuild_database.setBackgroundColor(color);
            color = ResourcesCompat.getColor(getResources(), R.color.primaryTextColor, null);
            rebuild_database.setTitleColor(color);
            Constants.Settings.there_is_update = false;
        }


        databaseVersionPreference.setSummary(String.valueOf(currentVersion));
    }

    @Override
    public void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(getActivity()).unregisterOnSharedPreferenceChangeListener(listner);
    }

    @Override
    public void onStop() {
        super.onStop();
        PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(listner);
    }


    @Override
    public FragmentManager getSupportFragmentManager() {
        return getFragmentManager();
    }
}
