package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.optc.optcdbmobile.data.database.OPTCDatabaseRepository;
import com.optc.optcdbmobile.data.database.entities.Captain;
import com.optc.optcdbmobile.data.database.entities.CaptainDescription;
import com.optc.optcdbmobile.data.database.entities.Limit;
import com.optc.optcdbmobile.data.database.entities.Potential;
import com.optc.optcdbmobile.data.database.entities.PotentialDescription;
import com.optc.optcdbmobile.data.database.entities.SailorDescription;
import com.optc.optcdbmobile.data.database.entities.Special;
import com.optc.optcdbmobile.data.database.entities.SpecialDescription;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UnitDialogViewModel extends AndroidViewModel {


    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private OPTCDatabaseRepository repo;

    public UnitDialogViewModel(@NonNull Application application) {
        super(application);

        repo = OPTCDatabaseRepository.getInstance(application);


    }

    public boolean unitHasCaptain(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return repo.unitHasCaptain(id);
            }
        }).get();
    }

    public boolean unitHasSpecial(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return repo.unitHasSpecial(id);
            }
        }).get();
    }

    public boolean unitHasSailor(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return repo.unitHasSailor(id);
            }
        }).get();
    }

    public boolean unitHasPotential(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return repo.unitHasPotential(id);
            }
        }).get();
    }

    public boolean unitHasLimit(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return repo.unitHasLimit(id);
            }
        }).get();
    }

    public List<CaptainDescription> getCaptainDescriptions(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<List<CaptainDescription>>() {
            @Override
            public List<CaptainDescription> call() throws Exception {
                return repo.getCaptainDescriptions(id);
            }
        }).get();
    }

    public Captain getCaptain(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<Captain>() {
            @Override
            public Captain call() throws Exception {
                return repo.getCaptain(id);
            }
        }).get();
    }

    public Special getSpecial(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<Special>() {
            @Override
            public Special call() throws Exception {
                return repo.getSpecial(id);
            }
        }).get();
    }

    public List<SpecialDescription> getSpecialDescriptions(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<List<SpecialDescription>>() {
            @Override
            public List<SpecialDescription> call() throws Exception {
                return repo.getSpecialDescriptions(id);
            }
        }).get();
    }


    public List<SailorDescription> getSailorDescriptions(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<List<SailorDescription>>() {
            @Override
            public List<SailorDescription> call() throws Exception {
                return repo.getSailorDescriptions(id);
            }
        }).get();
    }

    public List<Potential> getPotentials(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<List<Potential>>() {
            @Override
            public List<Potential> call() throws Exception {
                return repo.getPotentials(id);
            }
        }).get();
    }

    public List<Limit> getLimits(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<List<Limit>>() {
            @Override
            public List<Limit> call() throws Exception {
                return repo.getLimits(id);
            }
        }).get();
    }

    public List<PotentialDescription> getPotentialDescriptions(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<List<PotentialDescription>>() {
            @Override
            public List<PotentialDescription> call() throws Exception {
                return repo.getPotentialDescriptions(id);
            }
        }).get();
    }
}
