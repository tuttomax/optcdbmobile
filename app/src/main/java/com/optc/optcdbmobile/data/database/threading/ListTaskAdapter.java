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

import android.graphics.Color;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.optc.optcdbmobile.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ListTaskAdapter extends RecyclerView.Adapter<ListTaskAdapter.TaskViewHolder>
        implements TaskDelegate, TaskCallback {

    private static final byte PAYLOAD_OPERATION = 1;
    private static final byte PAYLOAD_STATE = 2;
    private static final byte PAYLOAD_CURRENT = 3;
    private static final byte PAYLOAD_MAX = 4;


    private final List<Task> list;

    private final android.os.Handler notifyHandler = new android.os.Handler(Looper.getMainLooper());
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);
    private final List<Future<?>> futureList = new ArrayList<>();
    private int currentIndex = 0;

    public ListTaskAdapter() {
        list = new ArrayList<>();
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
        Task task = getTask(taskViewHolder.getAdapterPosition());
        taskViewHolder.textView.setText(task.getOperation());
        taskViewHolder.progressTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position, @NonNull List<Object> payloads) {

        if (payloads.size() > 0) {

            Task task = getTask(holder.getAdapterPosition());

            for (Object obj : payloads) {
                Byte payload = (Byte) obj;
                switch (payload) {
                    case PAYLOAD_OPERATION:
                        holder.textView.setText(task.getOperation());
                        break;
                    case PAYLOAD_STATE: {
                        if (task.getState() == Task.ERROR) {
                            holder.progressTextView.setVisibility(View.VISIBLE);
                            holder.progressTextView.setText("ERROR");
                            holder.progressTextView.setTextColor(Color.RED);
                        } else if (task.getState() == Task.COMPLETED) {
                            holder.progressTextView.setVisibility(View.VISIBLE);
                            holder.progressTextView.setText("OK");
                            holder.progressTextView.setTextColor(Color.GREEN);
                        } else if ((task.getState() == Task.RUNNING) || (task.getState() == Task.CREATED)) {
                            holder.progressTextView.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                    case PAYLOAD_MAX: {
                    }
                    break;
                    case PAYLOAD_CURRENT: {
                        Float percent = ((float) task.getCurrent() / task.getMax()) * 100;
                        holder.progressTextView.setText(String.format("%d%%", percent.intValue()));
                    }
                    break;
                }

            }
        }

        onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void addTask(Task task) {

        final int finalIndex = currentIndex++;
        task.setIndex(finalIndex);
        list.add(task);
        futureList.add(executorService.submit(task));
    }

    @Override
    public void addTasks(final Task... tasks) {
        for (Task task : tasks) {
            addTask(task);
        }
        notifyHandler.post(new Runnable() {
            @Override
            public void run() {
                notifyItemRangeInserted(currentIndex, tasks.length);
            }
        });
    }

    @Override
    public Task getTask(int i) {
        return list.get(i);
    }

    @Override
    public void updateOperation(int i, String operation) {
        //list.get(i).setLabel(operation);
        notifyItemChanged(i, PAYLOAD_OPERATION);
    }

    @Override
    public void updateState(int i, int state) {
        //list.get(i).setState(state);
        notifyItemChanged(i, PAYLOAD_STATE);
    }

    @Override
    public void updateMax(int i, int max) {
        //list.get(i).setMax(max);
        notifyItemChanged(i, PAYLOAD_MAX);
    }

    @Override
    public void increment(int i) {
        notifyItemChanged(i, PAYLOAD_CURRENT);
    }

    @Override
    public void onStateChanged(final int i, final int state) {
        notifyHandler.post(new Runnable() {
            @Override
            public void run() {
                updateState(i, state);
            }
        });
    }

    @Override
    public void onOperationChanged(final int i, final String operation) {
        notifyHandler.post(new Runnable() {
            @Override
            public void run() {
                updateOperation(i, operation);
            }
        });
    }

    @Override
    public void onCurrentChanged(final int i, int current) {
        notifyHandler.post(new Runnable() {
            @Override
            public void run() {
                increment(i);
            }
        });
    }

    @Override
    public void onMaxChanged(final int i, final int max) {
        notifyHandler.post(new Runnable() {
            @Override
            public void run() {
                updateMax(i, max);
            }
        });
    }

    @Override
    public void onError(String name, Exception ex) {
        executorService.shutdownNow();
        if (!executorService.isTerminated()) {
            termination();
        }
        throw new RuntimeException("Error inside " + name, ex.getCause());
    }


    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public TextView progressTextView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.task_operation_text);
            progressTextView = itemView.findViewById(R.id.task_operation_progress_text);
        }
    }


    public void awaitTermination(int seconds) {
        try {
            boolean allTerminated = executorService.awaitTermination(seconds, TimeUnit.SECONDS);
            if (!allTerminated) {
                for (Future future : futureList) {
                    if (!future.isDone() && !future.isCancelled())
                        future.get();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            executorService.shutdown();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void termination() {
        executorService.shutdown();
    }
}
