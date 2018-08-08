package com.optc.optcdbmobile.data.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;

import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.database.OPTCDatabaseRepository;

import java.util.List;

class SettingsActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();

        PreferenceManager.setDefaultValues(this, R.xml.pref_database, false);
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);

    }


    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
            addPreferencesFromResource(R.xml.pref_general);
            setHasOptionsMenu(true);

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

        private static final String TAG_DATABASE_VERSION = "pref_database_version";
        private static Preference.OnPreferenceClickListener database_version_click_listner = new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (preference.getKey().equals(preference.getContext().getString(R.string.pref_database_version_key))) {
                    Log.i(TAG_DATABASE_VERSION, "Checking and updating database version");
                    OPTCDatabaseRepository.getInstance(preference.getContext().getApplicationContext()).VerifyDatabase();
                    return true;
                }
                return false;
            }
        };
        private static Preference.OnPreferenceChangeListener database_version_change_listner = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                Log.i(TAG_DATABASE_VERSION, (String) o);
                if (o.getClass() != String.class) {
                    //throw new RuntimeException(String.format("Invalid data %s -> expected %s",o.getClass(),String.class));
                    Log.d(TAG_DATABASE_VERSION, String.format("Invalid data %s -> expected %s", o.getClass(), String.class));
                    return false;
                }
                preference.setSummary((String) o);

                return true;
            }
        };
        private static Preference.OnPreferenceChangeListener wifi_download_change_listner = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {

                boolean flag = (Boolean) o;
                if (flag) preference.setSummary(R.string.pref_wifi_only_summary_true);
                else preference.setSummary(R.string.pref_wifi_only_summary_false);

                //set auto_download = false
                preference.getSharedPreferences().edit().putBoolean(preference.getContext().getString(R.string.pref_auto_download_key), flag).commit();

                return true;
            }
        };

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_database);
            setHasOptionsMenu(true);

            Preference database_version_preference = findPreference(getString(R.string.pref_database_version_key));
            Preference wifi_only_preference = findPreference(getString(R.string.pref_wifi_only_key));

            database_version_preference.setOnPreferenceClickListener(database_version_click_listner);
            database_version_preference.setOnPreferenceChangeListener(database_version_change_listner);
            database_version_preference.setSummary(PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).getString(getString(R.string.pref_database_version_key), ""));

            wifi_only_preference.setOnPreferenceChangeListener(wifi_download_change_listner);


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
}
