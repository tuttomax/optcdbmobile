package com.optc.optcdbmobile.data.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.database.OPTCDatabase;
import com.optc.optcdbmobile.data.database.entities.BoosterEvolverLocation;
import com.optc.optcdbmobile.data.database.entities.ColiseumLocation;
import com.optc.optcdbmobile.data.database.entities.Evolution;
import com.optc.optcdbmobile.data.database.entities.Family;
import com.optc.optcdbmobile.data.database.entities.FamilyUnit;
import com.optc.optcdbmobile.data.database.entities.FortnightLocation;
import com.optc.optcdbmobile.data.database.entities.Location;
import com.optc.optcdbmobile.data.database.entities.LocationChallengeData;
import com.optc.optcdbmobile.data.database.entities.LocationDrops;
import com.optc.optcdbmobile.data.database.entities.RaidLocation;
import com.optc.optcdbmobile.data.database.entities.SpecialLocation;
import com.optc.optcdbmobile.data.database.entities.TrainingForestLocation;
import com.optc.optcdbmobile.data.database.entities.TreasureLocation;
import com.optc.optcdbmobile.data.database.entities.Unit;
import com.optc.optcdbmobile.data.optcdb.API;
import com.optc.optcdbmobile.data.optcdb.entities.Cooldown;
import com.optc.optcdbmobile.data.optcdb.entities.Detail;
import com.optc.optcdbmobile.data.optcdb.entities.FamilyContainer;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Deprecated
/*
    @deprecated: Use ParallelBuildingDatabaseAsyncTask.
    Don't delete in case of BUGS in ParallelBuildingDatabaseAsyncTask
*/
public class BuildDatabaseAsyncTask extends AsyncTask<Void, String, Integer> {
    private WeakReference<Context> refContext;
    private WeakReference<View> refView;

    private ProgressDialog dialog;
    private Integer newVersion;

    public BuildDatabaseAsyncTask(View view) {
        refContext = new WeakReference<>(view.getContext());
        refView = new WeakReference<>(view);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(refContext.get());
        dialog.setCancelable(false);
        dialog.show();

    }

    @Override
    protected void onProgressUpdate(String... values) {

        String state = values[0];

        if (!state.isEmpty()) {
            dialog.setMessage(state);
        }

    }

    @Override
    protected void onPostExecute(Integer result) {
        dialog.dismiss();
        if (result == 1) {
            PreferenceManager.getDefaultSharedPreferences(refContext.get()).edit().putInt(Constants.Settings.pref_database_version_key, newVersion).commit();
            Snackbar.make(refView.get(), "Database building complete", Snackbar.LENGTH_LONG).show();
        } else if (result == -1) {
            Snackbar.make(refView.get(), "Error building database", Snackbar.LENGTH_LONG)
                    .setActionTextColor(refView.get().getResources().getColor(R.color.secondaryLightColor))
                    .setAction("REDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!refContext.isEnqueued() && !refView.isEnqueued())
                                new BuildDatabaseAsyncTask(refView.get()).execute();
                        }
                    });
        }
    }

    @Override
    protected Integer doInBackground(Void... voids) {

        ExecutorService service = Executors.newFixedThreadPool(6);

        List<Unit> unitList = null;
        List<Detail> detailList = null;
        List<Cooldown> cooldownList = null;
        List<Evolution> evolutionList = null;
        List<Object> dropsList = null;
        List<FamilyContainer> familyList = null;

        publishProgress(("Downloading data...\nPlease wait"));


        final Future<List<Unit>> futureUnits = service.submit(new Callable<List<Unit>>() {
            @Override
            public List<Unit> call() throws Exception {
                return (List<Unit>) API.getData(Constants.APIType.UNITS_TYPE);
            }
        });

        final Future<List<Detail>> futureDetails = service.submit(new Callable<List<Detail>>() {
            @Override
            public List<Detail> call() throws Exception {
                return (List<Detail>) API.getData(Constants.APIType.DETAILS_TYPE);
            }
        });

        final Future<List<Cooldown>> futureCooldown = service.submit(new Callable<List<Cooldown>>() {
            @Override
            public List<Cooldown> call() throws Exception {
                return (List<Cooldown>) API.getData(Constants.APIType.COOLDOWNS_TYPE);
            }
        });

        final Future<List<Evolution>> futureEvolution = service.submit(new Callable<List<Evolution>>() {
            @Override
            public List<Evolution> call() throws Exception {
                return (List<Evolution>) API.getData(Constants.APIType.EVOLUTIONS_TYPE);
            }
        });

        final Future<List<Object>> futureDrops = service.submit(new Callable<List<Object>>() {
            @Override
            public List<Object> call() throws Exception {
                return (List<Object>) API.getData(Constants.APIType.DROPS_TYPE);
            }
        });
        final Future<List<FamilyContainer>> futureFamily = service.submit(new Callable<List<FamilyContainer>>() {
            @Override
            public List<FamilyContainer> call() throws Exception {
                return (List<FamilyContainer>) API.getData(Constants.APIType.FAMILIES_TYPE);
            }
        });


        try {
            boolean result = service.awaitTermination(10, TimeUnit.SECONDS);
            if (!result) {
                if (!futureUnits.isDone()) {
                    publishProgress(("Downloading remains units data"));
                    unitList = futureUnits.get();
                }
                if (!futureEvolution.isDone()) {
                    publishProgress(("Downloading reminas evolutions data"));
                    evolutionList = futureEvolution.get();
                }
                if (!futureCooldown.isDone()) {
                    publishProgress(("Downloading remains cooldowns data"));
                    cooldownList = futureCooldown.get();
                }
                if (!futureDrops.isDone()) {
                    publishProgress(("Downloading remains drops data"));
                    dropsList = futureDrops.get();
                }
                if (!futureDetails.isDone()) {
                    publishProgress(("Downloading remains details data"));
                    detailList = futureDetails.get();
                }
                if (!futureFamily.isDone()) {
                    publishProgress(("Downloading remains families data"));
                    familyList = futureFamily.get();
                }

                unitList = futureUnits.get();
                evolutionList = futureEvolution.get();
                cooldownList = futureCooldown.get();
                dropsList = futureDrops.get();
                detailList = futureDetails.get();
                familyList = futureFamily.get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return -1;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return -1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }


        try {

            OPTCDatabase database = OPTCDatabase.getInstance(refContext.get());
            database.clearAllTables();

/*            publishProgress( ("Populating units table..."));
            database.unitDAO().insert(unitList);
*/

            String msg = "Populating units table...";
            int count = 0;
            int max = unitList.size();
            publishProgress((msg));
            for (Unit unit : unitList) {
                database.unitDAO().insert(unit);
                count++;
                publishProgress(buildProgress(msg, max, count));
            }

            msg = "Populating family table and updating unit's family...";
            count = 0;
            max = familyList.size();
            publishProgress((msg));
            for (int index = 0; index < familyList.size(); index++) {

                FamilyContainer familyContainer = familyList.get(index);
                Family family = familyContainer.getFamily();
                List<Integer> listIds = familyContainer.getUnitIds();

                database.familyDAO().insert(family);

                for (int familyIndex = 0; familyIndex < listIds.size(); familyIndex++) {
                    database.familyUnitDAO().insert(new FamilyUnit(family.getId(), listIds.get(familyIndex)));
                }

                count++;
                publishProgress(buildProgress(msg, max, count));
            }


            msg = "Inserting all units details";
            count = 0;
            max = detailList.size();
            publishProgress((msg));

            for (Detail detail : detailList) {

                database.captainDAO().insert(detail.getCaptainList());
                database.captainDescriptionDAO().insert(detail.getCaptainDescriptionList());

                database.specialDAO().insert(detail.getSpecialList());
                database.specialDescriptionDAO().insert(detail.getSpecialDescriptionList());

                database.sailorDAO().insert(detail.getSailorList());
                database.sailorDescriptionDAO().insert(detail.getSailorDescriptionList());

                database.limitDAO().insert(detail.getLimitList());

                database.potentialDAO().insert(detail.getPotentialList());
                database.potentialDescriptionDAO().insert(detail.getPotentialDescriptionList());

                count++;
                publishProgress(buildProgress(msg, max, count));
            }


            count = 0;
            max = cooldownList.size();
            msg = "Updating cooldowns for special...";
            publishProgress((msg));
            for (Cooldown cooldown : cooldownList) {
                database.specialDescriptionDAO().updateCooldown(cooldown.getId(), cooldown.getMin(), cooldown.getMax());
                count++;
                publishProgress(buildProgress(msg, max, count));
            }


            count = 0;
            max = evolutionList.size();
            msg = "Populating evolutions table...";
            publishProgress((msg));
            //database.evolutionDAO().insert(evolutionList);
            for (Evolution evolution : evolutionList) {
                database.evolutionDAO().insert(evolution);
                count++;
                publishProgress(buildProgress(msg, max, count));
            }

            msg = "Populating drops table...";
            count = 0;
            max = dropsList.size();
            publishProgress((msg));


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

                count++;
                publishProgress(buildProgress(msg, max, count));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
        newVersion = (Integer) API.getData(Constants.APIType.VERSION_TYPE);
        return 1;
    }


    private String buildProgress(String msg, int max, int count) {
        return String.format("%s\n%d/%d", msg, count, max);
    }
}


