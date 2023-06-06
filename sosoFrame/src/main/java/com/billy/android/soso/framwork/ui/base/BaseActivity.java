package com.billy.android.soso.framwork.ui.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.navigation.fragment.NavHostFragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.billy.android.soso.framwork.utils.AdaptScreenUtils;
import com.billy.android.soso.framwork.utils.ScreenUtils;
import com.billy.android.soso.framwork.view.status.Gloading;
import com.billy.android.soso.framwork.view.status.IViewStatusAction;

public class BaseActivity extends AppCompatActivity implements IViewStatusAction {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        BarUtils.setStatusBarColor(this, Color.TRANSPARENT);
//        BarUtils.setStatusBarLightMode(this, true);
        super.onCreate(savedInstanceState);
//        new NavHostFragment();

        getLifecycle().addObserver(new DefaultLifecycleObserver() {
        });
    }

    /********************************************显示Toast***********************************************************************/
    protected void showLongToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    protected void showShortToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(int stringRes) {
        showLongToast(getApplicationContext().getString(stringRes));
    }

    protected void showShortToast(int stringRes) {
        showShortToast(getApplicationContext().getString(stringRes));
    }

    /********************************************显示加载、成功、失败、空 页面状态***********************************************************************/
    private Gloading.Holder mViewStatusHolder;
    private void initViewStatusConfig() {
        if (mViewStatusHolder == null) {
            //bind status view to activity root view by default
            mViewStatusHolder = Gloading.getDefault().wrap(this).withRetry(new Runnable() {
                @Override
                public void run() {
                    onLoadRetry();
                }
            });
        }
    }

    @Override
    public void showLoading() {
        initViewStatusConfig();
        mViewStatusHolder.showLoading();

    }

    @Override
    public void showLoadSuccess() {
        initViewStatusConfig();
        mViewStatusHolder.showLoadSuccess();

    }

    @Override
    public void showLoadFailed() {
        mViewStatusHolder.showLoadFailed();

    }

    @Override
    public void showEmpty() {
        mViewStatusHolder.showEmpty();
    }

    @Override
    public void onLoadRetry() {
        // override this method in subclass to do retry task
    }

    /********************************************利用事件驱动来控制加载、成功、失败、空 页面状态***********************************************************************/
    /**
     * 利用事件驱动来控制加载、成功、失败、空 页面状态
     * TODO: 2023/5/31 网络接口回调发送事件， 有可能事件的消费没有网络接口回调执行快，所以中间事件会被覆盖没有消费
     * TODO: NetObserverWrapper.  onNext  之后 马上调用onComplete
     * @param stateEventCode
     */
    protected void switchStateFromEventCode(int stateEventCode) {

        switch (stateEventCode) {
            case IViewStatusAction.EVENT_STATUS_LOADING:
                showLoading();
                break;
            case IViewStatusAction.EVENT_STATUS_LOAD_SUCCESS:
                showLoadSuccess();
                break;
            case IViewStatusAction.EVENT_STATUS_LOAD_FAILED:
                showLoadFailed();
                break;
            case IViewStatusAction.EVENT_STATUS_EMPTY_DATA:
                showEmpty();
                break;
            case IViewStatusAction.EVENT_STATUS_RETRY_DATA:
                onLoadRetry();
                break;
        }
    }

    /********************************************工具方法***********************************************************************/


    @Override
    public Resources getResources() {
        if (ScreenUtils.isPortrait()) {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 360);
        } else {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 640);
        }
    }

    protected void toggleSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    protected void openUrlInBrowser(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}