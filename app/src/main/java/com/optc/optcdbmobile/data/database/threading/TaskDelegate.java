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
