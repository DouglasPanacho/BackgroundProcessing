package com.doug.backgroundprocessing.BoundServices;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

public class BoundService extends Service {

    IBinder mBind = new LocalBinder();

    private MutableLiveData<Integer> count = new MutableLiveData<>();

    @Nullable
    @Override
    public IBinder onBind(final Intent intent) {
        return mBind;
    }

    public void startWork() {
        final Handler handler = new Handler(getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull final Message msg) {
                count.postValue(msg.getData().getInt("test"));
                return false;
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    count.postValue(i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public MutableLiveData<Integer> getCount() {
        return count;
    }

    public class LocalBinder extends Binder {

        public BoundService getService() {
            return BoundService.this;
        }
    }
}
