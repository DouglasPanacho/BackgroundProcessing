package com.doug.backgroundprocessing.Chronometer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


public class ChronometerService extends Service {


    private int mCount = 0;
    public static String INT_VALUE = "INT_VALUE";
    public static String STATUS_VALUE = "STATUS_VALUE";
    Handler handler;

    public static void startService(Context context, int counter) {
        Intent intent = new Intent(context, ChronometerService.class);
        intent.putExtra(ChronometerService.INT_VALUE, counter);
        context.startService(intent);
    }


    @Override
    public void onDestroy() {
        if (handler != null)
            handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mCount = intent.getIntExtra(ChronometerService.INT_VALUE, 0);
        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Intent broadCastIntent = new Intent();
                broadCastIntent.setAction(ChronometerLocalBroadcastReceiver.ACTION_CHRONOMETER);
                broadCastIntent.putExtra(ChronometerLocalBroadcastReceiver.VALUE_INT, mCount);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadCastIntent);
                mCount++;
                handler.postDelayed(this, 1000);
            }
        });

        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
