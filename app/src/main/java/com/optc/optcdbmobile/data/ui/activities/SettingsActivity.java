package com.optc.optcdbmobile.data.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.database.OPTCDatabaseRepository;
import com.optc.optcdbmobile.data.tasks.CheckDatabaseVersionAsyncTask;

import java.util.List;

public class SettingsActivity extends AppCompatPreferenceActivity {

    private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            if (key.equals(getString(R.string.pref_database_version_key))) {
                findPreference(key)
                        .setSummary(
                                PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this)
                                        .getInt(key, -1));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return fragmentName.equals(GeneralPreferenceFragment.class.getName()) ||
                fragmentName.equals(DatabasePreferenceFragment.class.getName());
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }


    public static class GeneralPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);

            PreferenceManager.setDefaultValues(getActivity(), R.xml.pref_general, false);
            addPreferencesFromResource(R.xml.pref_general);

        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }


    public static class DatabasePreferenceFragment extends PreferenceFragment {


        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            PreferenceManager.setDefaultValues(getActivity(), R.xml.pref_database, false);
            addPreferencesFromResource(R.xml.pref_database);
            setHasOptionsMenu(true);

            Integer version = PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt(getString(R.string.pref_database_version_key), -1);
            findPreference(getString(R.string.pref_database_version_key)).setSummary(String.valueOf(version));

            final Preference rebuild_database = findPreference(getString(R.string.pref_rebuild_database_key));

            rebuild_database.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    OPTCDatabaseRepository.getInstance(getActivity().getApplication()).BuildDatabase(getView());
                    return true;
                }
            });
        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }


        @Override
        public void onResume() {
            super.onResume();
            new CheckDatabaseVersionAsyncTask(PreferenceManager.getDefaultSharedPreferences(getActivity()), getView());
        }
    }
}
