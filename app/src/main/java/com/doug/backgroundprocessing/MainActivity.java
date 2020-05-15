package com.doug.backgroundprocessing;

import com.doug.backgroundprocessing.BoundServices.BoundService;
import com.doug.backgroundprocessing.JobSchedulers.Job;
import com.doug.backgroundprocessing.Work.Work;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {

    private BoundService mBoundService;
    TextView textview;
    ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textview = findViewById(R.id.textview);
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
        //  startWorker();
        startBoundServices();
    }

    private void startBoundServices() {
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(final ComponentName name, final IBinder service) {
                Log.d("Service", "Connected");
                BoundService.LocalBinder binder = (BoundService.LocalBinder) service;
                mBoundService = binder.getService();
                mBoundService.startWork();
                registerObserver();

            }

            @Override
            public void onServiceDisconnected(final ComponentName name) {
                Log.d("Service", "Disconnected");
            }
        };
        bindService(new Intent(getApplicationContext(), BoundService.class), serviceConnection,
                    BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }

    private void registerObserver() {
        mBoundService.getCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(final Integer integer) {
                textview.setText(String.valueOf(integer));
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void startJobsScheduler() {
        ComponentName componentName = new ComponentName(this, Job.class);
        JobInfo.Builder jobInfo = new JobInfo.Builder(12, componentName);
        jobInfo.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo.build());

    }

    private void startWorker() {
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(Work.class)
                .setConstraints(Work.createConstraints())
                .build();
        // we use this observable to get the information form the worker
        WorkManager.getInstance(this)
                .getWorkInfoByIdLiveData(oneTimeWorkRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(final WorkInfo workInfo) {
                        workInfo.getOutputData().getInt("teste", 0);
                    }
                });
//        WorkManager.getInstance(this).beginWith(oneTimeWorkRequest).enqueue().getResult();
        WorkManager.getInstance(this).cancelWorkById(oneTimeWorkRequest.getId());
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest).getResult();
    }
}
