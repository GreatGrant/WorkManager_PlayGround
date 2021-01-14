package com.gralliams.workmanagerplayground;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String DOWNLOAD_TAG = "download_task";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Create data object to be utilised in Worker Class: use this if yourWorRequest and Worker class  requires data */
        Data data = new Data.Builder()
                            .putInt(SampleWorker.NUMBER, 10)
                            .build();

        /* Create constraint for making request: use this in the WorkRequest using .setConstraints if you need constraints */
        Constraints constraints = new Constraints.Builder()
                                    .setRequiresCharging(true)
                                    .setRequiredNetworkType(NetworkType.CONNECTED)
                                    .build();

        /* Create WorkRequest: Can be one time or periodic. We'll be using one time in this case */
        OneTimeWorkRequest oneTimeDownloadRequest = new OneTimeWorkRequest.Builder(SampleWorker.class)
                                                .setInputData(data)
                                                .setConstraints(constraints)
//                                                 .setInitialDelay(5, TimeUnit.HOURS)
                                                .addTag(DOWNLOAD_TAG)
                                                 .build();

        //Create WorkManager Instance to enqueue the request: oneTimeWorkRequest in this case
//        WorkManager.getInstance(this).enqueue(oneTimeDownloadRequest);

        //Create WorkRequest: Periodic WorkRequest in this case, repeat interval indicates the number of times the request ought to be repeated
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(SampleWorker.class, 7, TimeUnit.DAYS)
                                                                            .setInputData(data)
                                                                            .setConstraints(constraints)
                                                                            .setInitialDelay(5, TimeUnit.HOURS)
                                                                            .addTag("PERIODIC")
                                                                            .build();
        WorkManager.getInstance(this).enqueue(periodicWorkRequest);

    }
}