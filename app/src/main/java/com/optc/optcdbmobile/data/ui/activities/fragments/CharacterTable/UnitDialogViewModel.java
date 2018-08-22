package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.optc.optcdbmobile.data.database.OPTCDatabaseRepository;

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


}
