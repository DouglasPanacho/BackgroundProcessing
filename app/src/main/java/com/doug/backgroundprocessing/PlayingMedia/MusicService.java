package com.doug.backgroundprocessing.PlayingMedia;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.doug.backgroundprocessing.R;


public class MusicService extends Service {


    private int mCount = 0;
    public static String INT_VALUE = "INT_VALUE";
    public static String STATUS_VALUE = "STATUS_VALUE";
    Handler handler;
    MediaPlayer mediaPlayer;


    // Binder given to clients
    private final IBinder iBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    public static void startService(Context context, int counter) {
        Intent intent = new Intent(context, MusicService.class);
        intent.putExtra(MusicService.INT_VALUE, counter);
        context.startService(intent);
    }


    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        if (handler != null)
            handler.removeCallbacksAndMessages(null);
        mediaPlayer.pause();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.test);
//        mCount = intent.getIntExtra(MusicService.INT_VALUE, 0);
        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Intent broadCastIntent = new Intent();
                broadCastIntent.setAction(MusicLocalBroadcastReceiver.ACTION_CHRONOMETER);
                broadCastIntent.putExtra(MusicLocalBroadcastReceiver.VALUE_INT, mediaPlayer.getCurrentPosition() / 1000);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadCastIntent);
                mCount++;
                handler.postDelayed(this, 1000);
            }
        });
        mediaPlayer.setVolume(1.0f, 1.0f);
        mediaPlayer.start();

        return Service.START_STICKY;
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void resume(){
        mediaPlayer.start();
    }

    public class LocalBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

}
