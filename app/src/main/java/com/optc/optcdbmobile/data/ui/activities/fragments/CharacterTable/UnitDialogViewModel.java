package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.optc.optcdbmobile.data.database.OPTCDatabaseRepository;
import com.optc.optcdbmobile.data.database.entities.Captain;
import com.optc.optcdbmobile.data.database.entities.CaptainDescription;
import com.optc.optcdbmobile.data.database.entities.Evolution;
import com.optc.optcdbmobile.data.database.entities.Limit;
import com.optc.optcdbmobile.data.database.entities.LocationInformation;
import com.optc.optcdbmobile.data.database.entities.Potential;
import com.optc.optcdbmobile.data.database.entities.PotentialDescription;
import com.optc.optcdbmobile.data.database.entities.SailorDescription;
import com.optc.optcdbmobile.data.database.entities.Special;
import com.optc.optcdbmobile.data.database.entities.SpecialDescription;
import com.optc.optcdbmobile.data.database.entities.Unit;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UnitDialogViewModel extends AndroidViewModel {

    //TODO Rewrite me in order to create a shared modelview
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private OPTCDatabaseRepository repo;

    public MutableLiveData<List<Evolution>> evolvesTo;
    public MutableLiveData<List<Evolution>> evolvesFrom;
    public MutableLiveData<List<LocationInformation>> familyDrops;
    public MutableLiveData<List<LocationInformation>> manualDrops;

    public UnitDialogViewModel(@NonNull Application application) {
        super(application);

        repo = OPTCDatabaseRepository.getInstance(application);

        evolvesTo = new MutableLiveData<>();
        evolvesFrom = new MediatorLiveData<>();
        familyDrops = new MutableLiveData<>();
        manualDrops = new MutableLiveData<>();
    }

    //region unitHas
    public boolean unitHasCaptain(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return repo.unitHasCaptain(id);
            }
        }).get();
    }

    public boolean unitHasSpecial(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return repo.unitHasSpecial(id);
            }
        }).get();
    }

    public boolean unitHasSailor(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return repo.unitHasSailor(id);
            }
        }).get();
    }

    public boolean unitHasPotential(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return repo.unitHasPotential(id);
            }
        }).get();
    }

    public boolean unitHasLimit(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return repo.unitHasLimit(id);
            }
        }).get();
    }

    public boolean unitHasEvolutions(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return repo.unitHasEvolutions(id);
            }
        }).get();
    }

    public boolean unitHasEvolvesFrom(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return repo.unitHasEvolvesFrom(id);
            }
        }).get();
    }

    public boolean unitHasManuals(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return repo.unitHasManuals(id);
            }
        }).get();
    }

    public boolean unitHasFamily(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return repo.unitHasFamily(id);
            }
        }).get();
    }

    //endregion

    public Unit getUnit(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<Unit>() {
            @Override
            public Unit call() {
                return repo.getUnit(id);
            }
        }).get();
    }


    public List<CaptainDescription> getCaptainDescriptions(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<List<CaptainDescription>>() {
            @Override
            public List<CaptainDescription> call() {
                return repo.getCaptainDescriptions(id);
            }
        }).get();
    }

    public Captain getCaptain(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<Captain>() {
            @Override
            public Captain call() {
                return repo.getCaptain(id);
            }
        }).get();
    }

    public Special getSpecial(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<Special>() {
            @Override
            public Special call() {
                return repo.getSpecial(id);
            }
        }).get();
    }

    public List<SpecialDescription> getSpecialDescriptions(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<List<SpecialDescription>>() {
            @Override
            public List<SpecialDescription> call() {
                return repo.getSpecialDescriptions(id);
            }
        }).get();
    }


    public List<SailorDescription> getSailorDescriptions(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<List<SailorDescription>>() {
            @Override
            public List<SailorDescription> call() {
                return repo.getSailorDescriptions(id);
            }
        }).get();
    }

    public List<Potential> getPotentials(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<List<Potential>>() {
            @Override
            public List<Potential> call() {
                return repo.getPotentials(id);
            }
        }).get();
    }

    public List<Limit> getLimits(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<List<Limit>>() {
            @Override
            public List<Limit> call() {
                return repo.getLimits(id);
            }
        }).get();
    }

    public List<PotentialDescription> getPotentialDescriptions(final int id) throws ExecutionException, InterruptedException {
        return executorService.submit(new Callable<List<PotentialDescription>>() {
            @Override
            public List<PotentialDescription> call() {
                return repo.getPotentialDescriptions(id);
            }
        }).get();
    }

    public void getEvolvesTo(final int id) throws ExecutionException, InterruptedException {
        evolvesTo.setValue(executorService.submit(new Callable<List<Evolution>>() {
            @Override
            public List<Evolution> call() {
                return repo.getEvolvesTo(id);
            }
        }).get());
    }

    public void getEvolvesFrom(final int id) throws ExecutionException, InterruptedException {
        evolvesFrom.setValue(executorService.submit(new Callable<List<Evolution>>() {
            @Override
            public List<Evolution> call() {
                return repo.getEvolvesFrom(id);
            }
        }).get());
    }


    public void getManualDropsOf(final int id) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                manualDrops.postValue(repo.getManualDropsOf(id));
            }
        });
    }

    public void getFamilyDropsOf(final int id) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                familyDrops.postValue(repo.getFamilyDropsOf(id));
            }
        });
    }
}
