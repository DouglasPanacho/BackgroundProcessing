package com.doug.backgroundprocessing.ThreadHandlers;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ThreadHandlers {

    public void startNewThread(final TextView view) {
        final Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                view.setText(String.valueOf(msg.getData().getInt("INT")));
            }
        };

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 10; i++) {
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putInt("INT", i);
                    message.setData(bundle);
                    handler.sendMessage(message);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

    }


}
