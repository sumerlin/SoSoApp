package com.billy.android.soso.framwork.ui.viewModel;

import androidx.lifecycle.ViewModel;

import com.billy.android.soso.framwork.data.livedata.SoMutableLiveData;
import com.billy.android.soso.framwork.view.status.IViewStatusAction;

/**
 * Author：summer
 * Description：
 * 1、具备通知view state 的功能
 */
public class StatusViewModel  extends ViewModel implements IViewStatusAction {
    private  final  SoMutableLiveData<Integer> stateCode = new SoMutableLiveData<>();

    public SoMutableLiveData<Integer> getStateCode() {
        return stateCode;
    }



    @Override
    public void showLoading() {
        stateCode.postValue(IViewStatusAction.EVENT_STATUS_LOADING);
    }

    @Override
    public void showLoadSuccess() {
        stateCode.postValue(IViewStatusAction.EVENT_STATUS_LOAD_SUCCESS);
    }

    @Override
    public void showLoadFailed() {
        stateCode.postValue(IViewStatusAction.EVENT_STATUS_LOAD_FAILED);
    }

    @Override
    public void showEmpty() {
        stateCode.postValue(IViewStatusAction.EVENT_STATUS_EMPTY_DATA);
    }

    @Override
    public void onLoadRetry() {
        stateCode.postValue(IViewStatusAction.EVENT_STATUS_RETRY_DATA);
    }

}
