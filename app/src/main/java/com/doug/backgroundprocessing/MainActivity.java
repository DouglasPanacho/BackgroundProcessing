package com.doug.backgroundprocessing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.doug.backgroundprocessing.AsyncTask.AsyncTaskExample;
import com.doug.backgroundprocessing.ThreadHandlers.ThreadHandlers;
import com.doug.backgroundprocessing.ThreadHandlers.ThreadPoolExample;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textview =  findViewById(R.id.textview);
//        ThreadHandlers threadHandlers = new ThreadHandlers();
//        threadHandlers.startNewThread(textview);
//        AsyncTaskExample asyncTaskExample = new AsyncTaskExample();
//        asyncTaskExample.startAsyncTask(textview);
        ThreadPoolExample threadPoolExample = new ThreadPoolExample();
        threadPoolExample.startNewThread(textview);
    }
}
