package com.doug.backgroundprocessing.CountdownTimer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


public class CountdownService extends Service {


    public static String INT_VALUE = "INT_VALUE";
    CountDownTimer countDownTimer;
    public static void startService(Context context, int counter) {
        Intent intent = new Intent(context, CountdownService.class);
        intent.putExtra(CountdownService.INT_VALUE, counter);
        context.startService(intent);
    }

    @Override
    public void onDestroy() {
        countDownTimer.cancel();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Intent broadCastIntent = new Intent();
        broadCastIntent.setAction(CountdownLocalBroadcastReceiver.ACTION_COUNTDOWN);
         countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long currentValue) {
                broadCastIntent.putExtra(CountdownLocalBroadcastReceiver.VALUE_INT, currentValue / 1000);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadCastIntent);
            }

            @Override
            public void onFinish() {
                stopSelf();
            }
        };
        countDownTimer.start();
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
