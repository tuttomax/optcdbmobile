package com.optc.optcdbmobile.data.ui.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.database.OPTCDatabase;

import java.util.HashMap;
import java.util.List;

public class SettingsActivity extends AppCompatPreferenceActivity {

    private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            if (key.equals(getString(R.string.pref_database_version_key))) {
                findPreference(key)
                        .setSummary(
                                PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this)
                                        .getString(key, ""));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();

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

            PreferenceManager.setDefaultValues(getActivity(), R.xml.pref_general, false);
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


        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            PreferenceManager.setDefaultValues(getActivity(), R.xml.pref_database, false);
            addPreferencesFromResource(R.xml.pref_database);
            setHasOptionsMenu(true);


            final Preference rebuild_database = findPreference(getString(R.string.pref_rebuild_database_key));

            rebuild_database.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                //TODO Something better than this
                @SuppressLint("StaticFieldLeak")  //TODO Remove me
                @Override
                public boolean onPreferenceClick(Preference preference) {


                    new AsyncTask<Void, Integer, Void>() {
                        private ProgressDialog dialog;
                        private final HashMap<Integer, String> map = new HashMap<Integer, String>() {{
                            put(1, "Downloading and parsing unit");
                            put(2, "Downloading and parsing  details");
                            put(3, "Downloading and parsing cooldowns");
                            put(4, "Downloading and parsing drops");
                            put(5, "Downloading and parsing evolution");

                            put(6, "Populating UNITS table");
                            put(7, "Populating CAPTAIN table");
                            put(8, "Populating SPECIAL table");
                            put(9, "Populating SAILOR table");
                            put(10, "Populating LIMIT table");
                            put(11, "Populating POTENTIAL table");

                            put(12, "Populating LOCATION and DROPS table");
                            put(13, "Populating EVOLUTION table");
                            put(14, "Populating LOCATION,DROPS,CHALLENGE table");
                        }};

                        @Override
                        protected void onPreExecute() {
                            dialog = new ProgressDialog(getActivity());
                            dialog.setProgress(0);
                            dialog.setTitle("Building database");
                            dialog.setMessage("Starting");
                            dialog.setMax(14);
                            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            dialog.setCancelable(false);
                            dialog.show();
                        }

                        @Override
                        protected void onProgressUpdate(Integer... values) {
                            Integer value = values[0];
                            dialog.setProgress(value);
                            dialog.setMessage(map.get(value));
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            dialog.dismiss();
                        }

                        @Override
                        protected Void doInBackground(Void... voids) {

                            OPTCDatabase database = OPTCDatabase.getInstance(getActivity());

//TODO Recode API
/*
  publishProgress(1);
                            long start = System.currentTimeMillis();
                            List<Unit> unitList = (List<Unit>) API.getData(Constants.UNITS_TYPE);
                            long end = System.currentTimeMillis();
                            long diff = end-start;
                            Log.i("task",String.format("Time: %d ms", diff));

                            publishProgress(2);
                            List<Detail> detailList = (List<Detail>) API.getData(Constants.DETAILS_TYPE);

                            publishProgress(3);
                            List<Cooldown> cooldownList = (List<Cooldown>) API.getData(Constants.COOLDOWNS_TYPE);

                            publishProgress(4);
                            List<Object> dropsList= (List<Object>) API.getData(Constants.DROPS_TYPE);

                            publishProgress(5);
                            List<Evolution> evolutionList = (List<Evolution>) API.getData(Constants.EVOLUTIONS_TYPE);

    publishProgress(6);
    database.unitDAO().insert(unitList);

                            for (Detail detail : detailList) {

                                publishProgress(7);
                                database.captainDAO().insert(detail.getCaptainList());
                                database.captainDescriptionDAO().insert(detail.getCaptainDescriptionList());


                                publishProgress(8);
                                database.specialDAO().insert(detail.getSpecialList());
                                database.specialDescriptionDAO().insert(detail.getSpecialDescriptionList());

                                publishProgress(9);
                                database.sailorDAO().insert(detail.getSailorList());
                                database.sailorDescriptionDAO().insert(detail.getSailorDescriptionList());

                                publishProgress(10);
                                database.limitDAO().insert(detail.getLimitList());

                                publishProgress(11);
                                database.potentialDAO().insert(detail.getPotentialList());
                                database.potentialDescriptionDAO().insert(detail.getPotentialDescriptionList());
                            }

                            publishProgress(12);
                            for (Cooldown cooldown : cooldownList) {
                                database.specialDescriptionDAO().updateCooldown(cooldown.getId(), cooldown.getMin(), cooldown.getMax());
                            }

                            publishProgress(13);
                            database.evolutionDAO().insert(evolutionList);

                            publishProgress(14);
                            for (Object obj : dropsList) {
                                if (obj instanceof Location) {
                                    database.locationDAO().insert((Location) obj);
                                } else if (obj instanceof LocationDrops) {
                                    database.locationDropsDAO().insert((LocationDrops) obj);
                                } else if (obj instanceof LocationChallengeData) {
                                    database.locationChallengeDataDAO().insert((LocationChallengeData) obj);
                                } else if (obj instanceof BoosterEvolverLocation) {
                                    database.boosterEvolverLocationDAO().insert((BoosterEvolverLocation) obj);
                                } else if (obj instanceof ColiseumLocation) {
                                    database.coliseumLocationDAO().insert((ColiseumLocation) obj);
                                } else if (obj instanceof FortnightLocation) {
                                    database.fortnightLocationDAO().insert((FortnightLocation) obj);
                                } else if (obj instanceof RaidLocation) {
                                    database.raidLocationDAO().insert((RaidLocation) obj);
                                } else if (obj instanceof SpecialLocation) {
                                    database.specialLocationDAO().insert((SpecialLocation) obj);
                                } else if (obj instanceof TrainingForestLocation) {
                                    database.trainingForestLocationDAO().insert((TrainingForestLocation) obj);
                                } else if (obj instanceof TreasureLocation) {
                                    database.treasureLocationDAO().insert((TreasureLocation) obj);
                                }
                            }
*/
                            return null;
                        }
                    }.execute();

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

    }
}
