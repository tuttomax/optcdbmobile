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
