package com.gralliams.workmanagerplayground;

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

import java.util.Observable;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String DOWNLOAD_TAG = "download_task";
    private static final String TAG = "MainActivity";

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
//                                                .setConstraints(constraints)
//                                                 .setInitialDelay(5, TimeUnit.HOURS)
                                                .addTag(DOWNLOAD_TAG)
                                                 .build();

        //Create WorkManager Instance to enqueue the request: oneTimeWorkRequest in this case
        WorkManager.getInstance(this).enqueue(oneTimeDownloadRequest);

        //To create WorkRequest: Periodic WorkRequest in this case, repeat interval indicates the number of times the request ought to be repeated
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(SampleWorker.class, 7, TimeUnit.DAYS)
                                                                            .setInputData(data)
                                                                            .setConstraints(constraints)
//                                                                            .setInitialDelay(5, TimeUnit.HOURS)
                                                                            .addTag("PERIODIC")
                                                                            .build();

        /*enqueue as shown below if this is the request you want to use*/
//        WorkManager.getInstance(this).enqueue(periodicWorkRequest);


        /*To Check work status */
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(oneTimeDownloadRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                Log.d(TAG, "onChanged: work status "+workInfo.getState());
            }
        });

        /*To cancel work  */
        WorkManager.getInstance(this).cancelWorkById(oneTimeDownloadRequest.getId());

        /* Chaining multiple works:*/
        /*rather than just enqueue each, use keyword .beginWit & .then for other works.
        You can use .then as many times as required for different works to be chained.
        Note: You can chain only OneTimeWorkRequest
        You must also create an output data in your Worker class and pass it as parameter to Result.success() in overridden doWork method
        The output data of the first request serves as the input of the next request
        Or still use the input data by passing it as a parameter to result.success() */
        WorkManager.getInstance(this).beginWith(oneTimeDownloadRequest)
                                            .then(oneTimeDownloadRequest)
                                            .then(oneTimeDownloadRequest)
                                            .enqueue();
    }
}