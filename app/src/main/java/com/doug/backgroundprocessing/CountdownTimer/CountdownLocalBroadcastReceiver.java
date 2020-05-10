package com.doug.backgroundprocessing.CountdownTimer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CountdownLocalBroadcastReceiver extends BroadcastReceiver {

    public static String ACTION_COUNTDOWN = "ACTION_COUNTDOWN";
    public static String VALUE_INT = "VALUE_INT";

    interface UpdateView {
        void updateCounter(long value);
    }

    private UpdateView mCallback;

    public CountdownLocalBroadcastReceiver(UpdateView callback) {
        this.mCallback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && ACTION_COUNTDOWN.equalsIgnoreCase(intent.getAction())) {
            mCallback.updateCounter(intent.getLongExtra(VALUE_INT, 1));
        }

    }
}
