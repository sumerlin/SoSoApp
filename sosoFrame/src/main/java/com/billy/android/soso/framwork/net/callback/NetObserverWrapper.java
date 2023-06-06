package com.billy.android.soso.framwork.net.callback;

import com.billy.android.soso.framwork.net.bean.ErrorInfo;
import com.billy.android.soso.framwork.net.bean.FinishInfo;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableObserver;
import rxhttp.wrapper.utils.LogUtil;


/**
 * 业务代码code， 属于具体业务，没办法同一
 * @param <T>
 */
public abstract class NetObserverWrapper<T> extends DisposableObserver<T> implements INetObserver<T> {
    private FinishInfo mFinishInfo = new FinishInfo();

    @Override
    protected void onStart() {
        super.onStart();
        this.onStarter();
    }


    @Override
    public void onNext(T data) {
        doOnSuccess(data);
        //这里成功后， 马上执行onComplete（）方法，所以上层有 LiveData 观察的注意事件 有可能被覆盖
    }

    @Override
    public void onError(@NonNull Throwable e) {
        doOnError(e);
    }

    @Override
    public void onComplete() {
        //不管是成功 还是失败 都会回调onFinish()
        this.onFinish(mFinishInfo);
    }

    @Override
    public void onFinish(FinishInfo finishInfo) {

    }

    /**
     * 处理成功
     * 业务逻辑操作检查，逻辑处理异常，避免退出
     *
     * @param o
     */
    protected void doOnSuccess(T o) {
        try {
            this.onSuccess(o);
        } catch (Throwable e) {
//            this.onFailure(new ErrorInfo(e));
            doOnError(e);
            LogUtil.log("onSuccess 业务异常", e);
            if (LogUtil.isDebug()) {
                throw e;
            }
        }
    }

    protected void doOnError(@NonNull Throwable e) {
        ErrorInfo errorInfo = new ErrorInfo(e);
        mFinishInfo.setResultCode(errorInfo.getErrorCode());
        mFinishInfo.setResultMsg(errorInfo.getErrorMsg());
        this.onFailure(errorInfo);
    }
}
