package com.gralliams.workmanagerplayground;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class SampleWorker extends Worker {
    private static final String TAG = "SampleWorker";

    private static final String NUMBER = "NUMBER";

    public SampleWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    //This method handles long running task on the background task
    @NonNull
    @Override
    public Result doWork() {
    //Get input data
        Data inputData = getInputData();
    //Gets int value of input data using its  key (Data object alreay in MainActivity)
       int number = inputData.getInt(NUMBER, -1);
        Log.d(TAG, "doWork: number "+number);
       //Mimicking network request
        for (int i = number; i > 0; i--){
            Log.d(TAG, "doWork: i was "+i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Result.failure();
            }
        }
        return Result.success();
    }
}