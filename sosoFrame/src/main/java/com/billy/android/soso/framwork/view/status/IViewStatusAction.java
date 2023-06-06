package com.billy.android.soso.framwork.view.status;

public interface IViewStatusAction {
    public static final int EVENT_STATUS_LOADING = 1;
    public static final int EVENT_STATUS_LOAD_SUCCESS = 2;
    public static final int EVENT_STATUS_LOAD_FAILED = 3;
    public static final int EVENT_STATUS_EMPTY_DATA = 4;
    public static final int EVENT_STATUS_RETRY_DATA = 5;

    // override this method in subclass to do retry task

    void showLoading();

    void showLoadSuccess();

    void showLoadFailed();

    void showEmpty();

    void onLoadRetry();

}
