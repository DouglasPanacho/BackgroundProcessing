package com.doug.backgroundprocessing.Chronometer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ChronometerLocalBroadcastReceiver extends BroadcastReceiver {

    public static String ACTION_CHRONOMETER = "ACTION_CHRONOMETER";
    public static String VALUE_INT = "VALUE_INT";

    interface UpdateView {
        void updateCounter(int value);
    }

    private UpdateView mCallback;

    public ChronometerLocalBroadcastReceiver(UpdateView callback) {
        this.mCallback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && ACTION_CHRONOMETER.equalsIgnoreCase(intent.getAction())) {
            mCallback.updateCounter(intent.getIntExtra(VALUE_INT, -1));
        }

    }
}
