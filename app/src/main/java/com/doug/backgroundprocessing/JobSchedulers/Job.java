package com.doug.backgroundprocessing.JobSchedulers;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class Job extends JobService {

    @Override
    public boolean onStartJob(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d("Job Started", "Current value = " + i);
                }

            }
        }).start();

        return true;
    }

    @Override
    public boolean onStopJob(final JobParameters params) {
        return false;
    }
}
