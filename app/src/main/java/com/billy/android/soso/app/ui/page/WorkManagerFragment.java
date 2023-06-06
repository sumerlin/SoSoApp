package com.billy.android.soso.app.ui.page;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.billy.android.soso.app.BR;
import com.billy.android.soso.app.R;
import com.billy.android.soso.app.databinding.FragmentWorkmanagerBinding;
import com.billy.android.soso.app.service.DownloadWorker;
import com.billy.android.soso.framwork.ui.page.DataBindingConfig;
import com.billy.android.soso.framwork.ui.page.VmDataBindingFragment;
import com.billy.android.soso.framwork.ui.viewModel.UiStateHolder;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

import rxhttp.wrapper.utils.LogUtil;

/**
 * Author：summer
 * Description：
 */

public class WorkManagerFragment  extends VmDataBindingFragment {

    WorkManagerUiState mWorkManagerUiState;
    @Override
    protected void initViewModel() {

         mWorkManagerUiState = getFragmentScopeViewModel(WorkManagerUiState.class);

    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_workmanager, BR.workManagerUiState,mWorkManagerUiState);
    }
    FragmentWorkmanagerBinding binding;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding   = (FragmentWorkmanagerBinding) getBinding();
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkManager.getInstance(mActivity).cancelAllWorkByTag("WorkRequest");
            }
        });
        binding.btnWorkManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initWorkManager();
            }
        });

        observeWorkManager();
    }

    private void initWorkManager(){

        OneTimeWorkRequest request =  new OneTimeWorkRequest.Builder(DownloadWorker.class)
                .addTag("WorkRequest")
                .build();
        WorkManager.getInstance(mActivity)
                .enqueueUniqueWork("WorkRequest", ExistingWorkPolicy.REPLACE,request);


    }

    private void observeWorkManager(){
        LiveData<List<WorkInfo>> workInfoLiveData = WorkManager.getInstance(mActivity).getWorkInfosByTagLiveData("WorkRequest");
        workInfoLiveData.observe(mActivity, new Observer<List<WorkInfo>>() {
            @Override
            public void onChanged(List<WorkInfo> workInfos) {
                LogUtil.log("===========observe==onChanged====");
                if(workInfos!= null && workInfos.size()>0){
                    WorkInfo workInfo = workInfos.get(0);
                    WorkInfo.State state = workInfo.getState();
                    Data data = workInfo.getProgress();
//                    data.get
                    LogUtil.log("===========observe==isFinished===="+state.isFinished());
                }

            }
        });
    }




    public static class WorkManagerUiState extends UiStateHolder{

    }
}
