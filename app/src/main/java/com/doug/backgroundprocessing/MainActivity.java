package com.doug.backgroundprocessing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.doug.backgroundprocessing.Chronometer.ChronometerActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textview = findViewById(R.id.textview);
//        ThreadHandlers threadHandlers = new ThreadHandlers();
//        threadHandlers.startNewThread(textview);
//        AsyncTaskExample asyncTaskExample = new AsyncTaskExample();
//        asyncTaskExample.startAsyncTask(textview);
//        ThreadPoolExample threadPoolExample = new ThreadPoolExample();
//        threadPoolExample.startNewThread(textview);
        Intent intent = new Intent(this, ChronometerActivity.class);
        startActivity(intent);
    }
}
