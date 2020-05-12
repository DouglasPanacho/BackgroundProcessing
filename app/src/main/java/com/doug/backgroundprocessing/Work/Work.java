package com.doug.backgroundprocessing.Work;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class Work extends Worker {

    public Work(@NonNull final Context context, @NonNull final WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.d("Job Thread", Thread.currentThread().getName());
            Log.d("Job Started", "Current value = " + i);
        }


        return Result.success();
    }

    public static Constraints createConstraints(){
        // This is not a must, if we don´t set the constraints the work will execute as normal
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.UNMETERED).build();
        return constraints;
    }
}
