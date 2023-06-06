package com.billy.android.soso.app.service;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.Executor;

import rxhttp.wrapper.utils.LogUtil;

/**
 * Author：summer
 * Description：
 */
public class DownloadWorker extends Worker {

    public DownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        Data data = getInputData();
        String filePath = data.getString("filePath");
        Data newData = new Data.Builder().putString("filePath", filePath).build();
        LogUtil.log("===========doWork======");
//        Thread.currentThread().wait();
        try {
            Thread.sleep(1000*10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return Result.success(newData);
    }

}
