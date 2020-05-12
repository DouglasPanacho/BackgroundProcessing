package com.doug.backgroundprocessing;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.doug.backgroundprocessing.Chronometer.ChronometerActivity;
import com.doug.backgroundprocessing.CountdownTimer.CountdownActivity;
import com.doug.backgroundprocessing.JobSchedulers.Job;
import com.doug.backgroundprocessing.PlayingMedia.MusicActivity;
import com.doug.backgroundprocessing.ThreadHandlers.ThreadHandlers;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textview = findViewById(R.id.textview);
//        ThreadHandlers threadHandlers = new ThreadHandlers();
//        threadHandlers.startNewThread(textview);
//        AsyncTaskExample asyncTaskExample = new AsyncTaskExample();
//        asyncTaskExample.startAsyncTask(textview);
//        ThreadPoolExample threadPoolExample = new ThreadPoolExample();
//        threadPoolExample.startNewThread(textview);
//        Intent intent = new Intent(this, ChronometerActivity.class);
//        startActivity(intent);
//        Intent intent = new Intent(this, CountdownActivity.class);
//        startActivity(intent);
//        Intent intent = new Intent(this, MusicActivity.class);
//        startActivity(intent);
        startJobsScheduler();
    }

    private void startJobsScheduler() {
        ComponentName componentName = new ComponentName(this, Job.class);
        JobInfo.Builder jobInfo = new JobInfo.Builder(12, componentName);
        jobInfo.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo.build());

    }
}
