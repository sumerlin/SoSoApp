package com.billy.android.soso.app.ui.viewmodel;

import com.billy.android.soso.app.test.InfoList;
import com.billy.android.soso.framwork.data.livedata.SoMutableLiveData;
import com.billy.android.soso.framwork.net.NetBuilder;
import com.billy.android.soso.framwork.net.NetRequestHelper;
import com.billy.android.soso.framwork.net.bean.ErrorInfo;
import com.billy.android.soso.framwork.net.bean.FinishInfo;
import com.billy.android.soso.framwork.net.callback.NetObserverWrapper;
import com.billy.android.soso.framwork.ui.viewModel.StatusViewModel;

import java.util.ArrayList;
import java.util.List;

import rxhttp.wrapper.utils.LogUtil;

/**
 * Author：summer
 * Description：
 */
public class RequestViewModel  extends StatusViewModel {

    private final SoMutableLiveData<InfoList> mInfoList = new SoMutableLiveData<>();
    public SoMutableLiveData<InfoList> getInfoList(){
        return this.mInfoList;
    }

    public  void netRequest() {
       NetRequestHelper.<InfoList>get("https://www.wanandroid.com/article/list/0/json")
                .toObservableResult(InfoList.class)
                .subscribe(new NetObserverWrapper<InfoList>() {
                    @Override
                    public void onStarter() {
                        LogUtil.log("=========onStarter===============");
                        showLoading();
                    }

                    @Override
                    public void onSuccess(InfoList data) {
                        LogUtil.log("=========onSuccess===============");
//                        Toast.makeText(MainActivity.this, "onSuccess", Toast.LENGTH_SHORT).show();
                        mInfoList.setValue(data);
                        showLoadSuccess();
                    }

                    @Override
                    public void onFailure(ErrorInfo error) {
                        LogUtil.log("=========onFailure===============");
                        showLoadFailed();

                    }

                    @Override
                    public void onFinish(FinishInfo finishInfo) {
                        super.onFinish(finishInfo);
                        LogUtil.log("=========onFinish===============");
                    }
                });

    }
}
