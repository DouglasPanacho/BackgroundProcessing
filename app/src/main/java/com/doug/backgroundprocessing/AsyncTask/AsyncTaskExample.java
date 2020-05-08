package com.doug.backgroundprocessing.AsyncTask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class AsyncTaskExample {

    public void startAsyncTask(final TextView view) {
        AsyncT asyncT = new AsyncT(new Callback() {
            @Override
            public void updateProgress(final int value) {
                view.setText(String.valueOf(value));
            }
        });
        asyncT.execute();

    }

    interface Callback {
        void updateProgress(int value);
    }

    private class AsyncT extends AsyncTask<Void, Integer, Void> {

        Callback mCallback;

        public AsyncT(Callback callback) {
            mCallback = callback;
        }

        @Override
        protected Void doInBackground(final Void... voids) {
            onProgressUpdate();
            for (int i = 0; i < 10; i++) {
                publishProgress(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(final Integer... values) {
            if(values!=null && values.length>0)
            mCallback.updateProgress(values[0]);
        }
    }
}
