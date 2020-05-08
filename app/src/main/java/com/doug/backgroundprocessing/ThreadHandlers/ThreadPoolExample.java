package com.doug.backgroundprocessing.ThreadHandlers;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;

public class ThreadPoolExample {
    private BlockingQueue<Runnable> decodeWorkQueue;

    private static final int KEEP_ALIVE_TIME = 1;
    // Sets the Time Unit to seconds
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    private volatile int count = 0;
    private List<String> list = new ArrayList<>();

    public void startNewThread(final TextView view) {
        decodeWorkQueue = new LinkedBlockingQueue<Runnable>();

        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                NUMBER_OF_CORES,       // Initial pool size
                NUMBER_OF_CORES,       // Max pool size
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                decodeWorkQueue);
        final Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                view.setText("");
                for (int i = 0; i < list.size(); i++) {
                    view.append(" " + list.get(i));
                }
            }
        };
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < 10; i++) {
                    count = i;
                    update();
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putInt("INT", count);
                    message.setData(bundle);
                }
                handler.sendMessage(new Message());
            }
        });
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    count = i + 10;
                    update();
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("NAME", Thread.currentThread().getName());
                    bundle.putInt("INT", count);
                    message.setData(bundle);

                }
                handler.sendMessage(new Message());
            }
        });
    }


    public synchronized void update() {
        list.add(String.valueOf(count));
    }
}
