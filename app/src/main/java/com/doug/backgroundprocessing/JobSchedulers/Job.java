package com.doug.backgroundprocessing.JobSchedulers;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class Job extends JobService {

    @Override
    public boolean onStartJob(final JobParameters params) {
        for (int i = 0; i < 100; i++) {
            Log.d("Job Started", "Current value = " + i);
        }

        return true;
    }

    @Override
    public boolean onStopJob(final JobParameters params) {
        return true;
    }
}
