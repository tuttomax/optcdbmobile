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
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.optc.optcdbmobile.data.database.OPTCDatabaseRepository;
import com.optc.optcdbmobile.data.database.entities.Unit;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainViewModel extends AndroidViewModel {

    private final ExecutorService executorService = Executors.newFixedThreadPool(3);

    private OPTCDatabaseRepository repository;

    public MutableLiveData<List<Unit>> units;

    public MainViewModel(@NonNull Application application) {
        super(application);

        repository = OPTCDatabaseRepository.getInstance(application);

        units = new MutableLiveData<>();

    }

    public void getUnits() {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                units.postValue(repository.getUnits());
            }
        });
    }


    public void getUnitsWithName(final String name) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                units.postValue(repository.getUnitsWithName(name));
            }
        });
    }



}
