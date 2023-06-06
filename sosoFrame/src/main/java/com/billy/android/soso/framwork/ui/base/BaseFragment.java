package com.billy.android.soso.framwork.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.billy.android.soso.framwork.view.status.Gloading;
import com.billy.android.soso.framwork.view.status.IViewStatusAction;

public abstract class BaseFragment extends Fragment implements IViewStatusAction {

    protected AppCompatActivity mActivity;
    private static final Handler HANDLER = new Handler();
    protected boolean mAnimationLoaded;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        mRootView = intiCreateView(inflater, container, savedInstanceState);
        mRootView = initViewStatusConfig();
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TODO:需要添加吗？
//        addOnBackPressed();

    }

    @Nullable
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        HANDLER.postDelayed(() -> {
            if (!mAnimationLoaded) {
                mAnimationLoaded = true;
                loadInitData();
            }
        }, 280);
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    protected void loadInitData() {

    }


    private View mRootView;

    public abstract View intiCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /********************************************显示加载、成功、失败、空 页面状态***********************************************************************/
    private Gloading.Holder mViewStatusHolder;

    private View initViewStatusConfig() {
        if (mViewStatusHolder == null) {
            //bind status view to activity root view by default
            mViewStatusHolder = Gloading.getDefault().wrap(mRootView).withRetry(new Runnable() {
                @Override
                public void run() {
                    onLoadRetry();
                }
            });
        }
        return mViewStatusHolder.getWrapper();
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


    /********************************************显示Toast***********************************************************************/


    protected void showLongToast(String text) {
        Toast.makeText(mActivity.getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    protected void showShortToast(String text) {
        Toast.makeText(mActivity.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(int stringRes) {
        showLongToast(mActivity.getApplicationContext().getString(stringRes));
    }

    protected void showShortToast(int stringRes) {
        showShortToast(mActivity.getApplicationContext().getString(stringRes));
    }


    /********************************************工具方法***********************************************************************/

    protected void toggleSoftInput() {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    protected void openUrlInBrowser(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    protected Context getApplicationContext() {
        return mActivity.getApplicationContext();
    }

    /********************************************NavHostFragment 控制器***********************************************************************/


    private void addOnBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                onBackPressed();
            }
        });
    }

    /**
     * 针对Navigation  方案的返回键
     */
    protected void onBackPressed() {
        nav().navigateUp();
    }
    protected NavController nav() {
        return NavHostFragment.findNavController(this);
    }




}
