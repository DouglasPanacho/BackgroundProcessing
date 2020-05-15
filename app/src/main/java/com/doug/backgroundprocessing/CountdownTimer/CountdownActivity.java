package com.doug.backgroundprocessing.CountdownTimer;

import com.doug.backgroundprocessing.R;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class CountdownActivity extends AppCompatActivity implements CountdownLocalBroadcastReceiver.UpdateView {


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
        CountdownLocalBroadcastReceiver receiver = new CountdownLocalBroadcastReceiver(this);
        intentFilter.addAction(CountdownLocalBroadcastReceiver.ACTION_COUNTDOWN);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);
    }

    public void startService(View view) {
        CountdownService.startService(this, counter);
    }


    public void stopService(View view) {
        stopService(new Intent(this, CountdownService.class));
    }

    public void resetService(View view) {
        stopService(new Intent(this, CountdownService.class));
        CountdownService.startService(this, counter);
    }


    @Override
    public void updateCounter(long value) {
        mCounterTextView.setText(String.valueOf(value));
    }
}
