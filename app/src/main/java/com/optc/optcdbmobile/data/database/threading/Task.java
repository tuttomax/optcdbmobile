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

package com.optc.optcdbmobile.data.database.threading;

public abstract class Task implements Runnable {


    public final static int ERROR = -1;
    public final static int CREATED = 0;
    public final static int RUNNING = 1;
    public final static int COMPLETED = 2;

    private String operation;
    private int state;
    private int max;
    private int current;
    private int index;

    private TaskCallback callback;

    public Task(String operation, TaskCallback callback) {
        this.operation = operation;
        state = CREATED;

        current = 0;
        max = 0;

        this.callback = callback;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        callback.onStateChanged(index, state);
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
        callback.onMaxChanged(index, max);
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getCurrent() {
        return current;
    }

    public void increment() {
        current++;
        callback.onCurrentChanged(index, current);
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
        callback.onOperationChanged(index, operation);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setError(Exception ex) {
        setState(ERROR);
        callback.onError(ex);
    }

}
