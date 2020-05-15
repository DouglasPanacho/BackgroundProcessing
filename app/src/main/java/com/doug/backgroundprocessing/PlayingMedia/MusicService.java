package com.doug.backgroundprocessing.PlayingMedia;

import com.doug.backgroundprocessing.R;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


public class MusicService extends Service {

    public static String INT_VALUE = "INT_VALUE";
    Handler handler;
    MediaPlayer mediaPlayer;


    private Notification createNotification() {
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MusicActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "chanel")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("textTitle")
                .setContentText("textContent")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        return builder.build();
    }

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
        createNotification();
        startForeground(1, createNotification());

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

    public void resume() {
        mediaPlayer.start();
    }

    public class LocalBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "name";
            String description = "test";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("chanel", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
