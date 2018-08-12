package com.optc.optcdbmobile.data.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

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
import com.optc.optcdbmobile.data.optcdb.Constants;
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

public class BuildDatabaseAsyncTask extends AsyncTask<Void, State, Void> {
    private WeakReference<Context> refContext;

    BuildDatabaseAsyncTask(Context context) {
        refContext = new WeakReference<>(context);
    }


    private ProgressDialog dialog;
    //TODO Create ProgressDialogDelegate

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(refContext.get());
        dialog.setCancelable(false);
        dialog.setIndeterminate(true);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Ready");
        dialog.show();
    }

    @Override
    protected void onProgressUpdate(State... values) {
        super.onProgressUpdate(values);

        State state = values[0];

        /*
        if (state.isCommandValid()) {
            if (state.getCommand().equals("+max")) {
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.setMax(state.getValue());
            }

            if (state.getCommand().equals("-max")) {
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMax(0);
            }

            if (state.getCommand().equals("increment")) {
                dialog.incrementProgressBy(1);
            }
        }
        */

        if (state.isMessageValid()) {
            dialog.setMessage(state.getMessage());
        }

    }

    @Override
    protected void onPostExecute(Void a) {
        super.onPostExecute(a);
        dialog.dismiss();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        ExecutorService service = Executors.newFixedThreadPool(6);

        List<Unit> unitList = null;
        List<Detail> detailList = null;
        List<Cooldown> cooldownList = null;
        List<Evolution> evolutionList = null;
        List<Object> dropsList = null;
        List<FamilyContainer> familyList = null;

        publishProgress(new State("Downloading data...\nPlease wait"));

        final Future<List<Unit>> futureUnits = service.submit(new Callable<List<Unit>>() {
            @Override
            public List<Unit> call() throws Exception {
                return (List<Unit>) API.getData(Constants.UNITS_TYPE);
            }
        });

        final Future<List<Detail>> futureDetails = service.submit(new Callable<List<Detail>>() {
            @Override
            public List<Detail> call() throws Exception {
                return (List<Detail>) API.getData(Constants.DETAILS_TYPE);
            }
        });

        final Future<List<Cooldown>> futureCooldown = service.submit(new Callable<List<Cooldown>>() {
            @Override
            public List<Cooldown> call() throws Exception {
                return (List<Cooldown>) API.getData(Constants.COOLDOWNS_TYPE);
            }
        });

        final Future<List<Evolution>> futureEvolution = service.submit(new Callable<List<Evolution>>() {
            @Override
            public List<Evolution> call() throws Exception {
                return (List<Evolution>) API.getData(Constants.EVOLUTIONS_TYPE);
            }
        });

        final Future<List<Object>> futureDrops = service.submit(new Callable<List<Object>>() {
            @Override
            public List<Object> call() throws Exception {
                return (List<Object>) API.getData(Constants.DROPS_TYPE);
            }
        });
        final Future<List<FamilyContainer>> futureFamily = service.submit(new Callable<List<FamilyContainer>>() {
            @Override
            public List<FamilyContainer> call() throws Exception {
                return (List<FamilyContainer>) API.getData(Constants.FAMILIES_TYPE);
            }
        });


        try {
            boolean result = service.awaitTermination(10, TimeUnit.SECONDS);
            if (!result) {
                if (!futureUnits.isDone()) {
                    publishProgress(new State("Downloading remains units data"));
                    unitList = futureUnits.get();
                }
                if (!futureEvolution.isDone()) {
                    publishProgress(new State("Downloading reminas evolutions data"));
                    evolutionList = futureEvolution.get();
                }
                if (!futureCooldown.isDone()) {
                    publishProgress(new State("Downloading remains cooldowns data"));
                    cooldownList = futureCooldown.get();
                }
                if (!futureDrops.isDone()) {
                    publishProgress(new State("Downloading remains drops data"));
                    dropsList = futureDrops.get();
                }
                if (!futureDetails.isDone()) {
                    publishProgress(new State("Downloading remains details data"));
                    detailList = futureDetails.get();
                }
                if (!futureFamily.isDone()) {
                    publishProgress(new State("Downloading remains families data"));
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
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        try {

            OPTCDatabase database = OPTCDatabase.getInstance(refContext.get());
            database.clearAllTables();

            publishProgress(new State("Populating units table..."));
            database.unitDAO().insert(unitList);


            publishProgress(new State("Populating family table and updating unit's family...",
                    "+max", familyList.size()));
            for (int index = 0; index < familyList.size(); index++) {
                FamilyContainer familyContainer = familyList.get(index);
                Family family = familyContainer.getFamily();
                List<Integer> listIds = familyContainer.getUnitIds();

                database.familyDAO().insert(family);

                for (int familyIndex = 0; familyIndex < listIds.size(); familyIndex++) {
                    database.familyUnitDAO().insert(new FamilyUnit(family.getId(), listIds.get(familyIndex)));
                }
                publishProgress(new State("increment", 0));
            }
            publishProgress(new State("-max", 0));


            publishProgress(new State("Inserting all units details", "+max", detailList.size()));
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

                publishProgress(new State("increment", 0));
            }
            publishProgress(new State("-max", 0));


            publishProgress(new State("Populating cooldowns for special..."));
            for (Cooldown cooldown : cooldownList) {
                database.specialDescriptionDAO().updateCooldown(cooldown.getId(), cooldown.getMin(), cooldown.getMax());
            }

            publishProgress(new State("Populating evolutions table..."));
            database.evolutionDAO().insert(evolutionList);

            publishProgress(new State("Populating drops table...", "+max", dropsList.size()));
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
                publishProgress(new State("increment", 0));
            }
            publishProgress(new State("-min", 0));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}


