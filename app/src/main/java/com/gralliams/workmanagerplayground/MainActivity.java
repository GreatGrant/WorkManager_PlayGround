package com.gralliams.workmanagerplayground;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Observable;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String DOWNLOAD_TAG = "download_task";
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creating a data object
        //to pass the data with workRequest
        //we can put as many variables needed
        Data data = new Data.Builder()
                .putString(SampleWorker.TASK_DESC, "The task data passed from MainActivity")
                .build();

        final OneTimeWorkRequest workRequest =
                new OneTimeWorkRequest.Builder(SampleWorker.class)
                        .setInputData(data)
                        .build();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkManager.getInstance(getApplicationContext()).enqueue(workRequest);
            }
        });

        final TextView textView = findViewById(R.id.textView_status);
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(@Nullable WorkInfo workInfo) {
                        assert workInfo != null;
                        textView.append(workInfo.getState().name() + "\n");
                    }
                });
    }
    }
