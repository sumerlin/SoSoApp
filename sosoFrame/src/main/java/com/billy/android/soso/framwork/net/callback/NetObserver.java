package com.billy.android.soso.framwork.net.callback;


import com.billy.android.soso.framwork.net.bean.ErrorInfo;

import io.reactivex.rxjava3.annotations.NonNull;

public abstract class NetObserver<T> {

    public abstract void onStart();

    public abstract void onSuccess(T data);

    public abstract void onFailure(@NonNull ErrorInfo error);

    public void onFinish() {

    }

}
