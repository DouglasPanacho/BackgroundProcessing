package com.doug.backgroundprocessing.PlayingMedia;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.doug.backgroundprocessing.MainActivity;
import com.doug.backgroundprocessing.R;

public class MusicActivity extends AppCompatActivity implements MusicLocalBroadcastReceiver.UpdateView {


    TextView mCounterTextView;
    MusicService player;
    boolean serviceBound;
    int counter = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chronometer_activity);
        mCounterTextView = findViewById(R.id.textview);
        setBroadcast();
    }

    private void setBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        MusicLocalBroadcastReceiver receiver = new MusicLocalBroadcastReceiver(this);
        intentFilter.addAction(MusicLocalBroadcastReceiver.ACTION_CHRONOMETER);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);
    }



    public void startService(View view) {


        if (!serviceBound) {
            Intent playerIntent = new Intent(this, MusicService.class);
            startForegroundService(playerIntent);
            bindService(playerIntent, serviceConnection, BIND_AUTO_CREATE);
        } else {
            //Service is active
            //Send media with BroadcastReceiver
        }
    }


    public void stopMusic(View view) {
        player.pause();
    }

    public void resetService(View view) {
       player.resume();
    }


    @Override
    public void updateCounter(int value) {
        counter = value;
        mCounterTextView.setText(String.valueOf(value));
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MusicService.LocalBinder binder = (MusicService.LocalBinder) service;
            player = binder.getService();
            serviceBound = true;

            Toast.makeText(MusicActivity.this, "Service Bound", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };




}
