package com.optc.optcdbmobile.data.database.threading;

public interface TaskDelegate {

    void addTask(Task task);

    void addTasks(Task... tasks);

    Task getTask(int i);

    void awaitTermination(int seconds);

    void updateOperation(int i, String operation);

    void updateState(int i, int state);

    void updateMax(int i, int max);

    void increment(int i);


}
