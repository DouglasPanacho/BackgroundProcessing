package com.doug.backgroundprocessing.Chronometer;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.doug.backgroundprocessing.R;

public class ChronometerActivity extends AppCompatActivity implements ChronometerLocalBroadcastReceiver.UpdateView {


    TextView mCounterTextView;
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
        ChronometerLocalBroadcastReceiver receiver = new ChronometerLocalBroadcastReceiver(this);
        intentFilter.addAction(ChronometerLocalBroadcastReceiver.ACTION_CHRONOMETER);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);
    }

    public void startService(View view) {
        ChronometerService.startService(this, counter);
    }


    public void stopService(View view) {
        stopService(new Intent(this, ChronometerService.class));
    }

    public void resetService(View view) {
        counter = 1;
        stopService(new Intent(this, ChronometerService.class));
        ChronometerService.startService(this, counter);
    }


    @Override
    public void updateCounter(int value) {
        counter = value;
        mCounterTextView.setText(String.valueOf(value));
    }
}
