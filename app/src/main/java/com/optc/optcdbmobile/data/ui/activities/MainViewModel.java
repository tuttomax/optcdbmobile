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

package com.optc.optcdbmobile.data.ui.activities;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.optc.optcdbmobile.data.database.OPTCDatabaseRepository;
import com.optc.optcdbmobile.data.database.entities.Unit;
import com.optc.optcdbmobile.data.optcdb.entities.Detail;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainViewModel extends AndroidViewModel {
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private OPTCDatabaseRepository repository;

    private LiveData<List<Unit>> units = new MutableLiveData<>();
    private MutableLiveData<Detail> currentUnitDetail;

    public MainViewModel(Application application) {
        super(application);
        repository = new OPTCDatabaseRepository(application);


        Future<LiveData<List<Unit>>> task = executorService.submit(new Callable<LiveData<List<Unit>>>() {
            @Override
            public LiveData<List<Unit>> call() throws Exception {
                return repository.getUnits();
            }
        });

        try {
            units = task.get(60, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }


    public LiveData<List<Unit>> getUnits() {
        return units;
    }

    public MutableLiveData<Detail> getCurrentUnitDetail() {
        return currentUnitDetail;
    }


}
