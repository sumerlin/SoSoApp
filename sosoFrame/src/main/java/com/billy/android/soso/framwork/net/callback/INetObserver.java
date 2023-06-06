package com.billy.android.soso.framwork.net.callback;

import com.billy.android.soso.framwork.net.bean.ErrorInfo;
import com.billy.android.soso.framwork.net.bean.FinishInfo;

import io.reactivex.rxjava3.annotations.NonNull;

public interface INetObserver<T> {
    public  void onStarter();

    public  void onSuccess(T data);

    public void onFailure(@NonNull ErrorInfo error);

    /**
     * 给特殊业务处理使用
     * 不管是成功 还是失败 都会回调onFinish()
     * @param finishInfo
     */
    public void onFinish(FinishInfo finishInfo) ;

}
