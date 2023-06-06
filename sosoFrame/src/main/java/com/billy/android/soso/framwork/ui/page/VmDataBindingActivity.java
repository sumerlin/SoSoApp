package com.billy.android.soso.framwork.ui.page;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.billy.android.soso.framwork.manager.NetworkStateManager;
import com.billy.android.soso.framwork.ui.viewModel.StatusViewModel;
import com.billy.android.soso.framwork.ui.viewModel.ViewModelScopeBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Author：summer
 * Description：
 * 1、提供ViewModel 创建功能， 且可以是不同的作用域范围的ViewModel
 */
public abstract class VmDataBindingActivity extends DataBindingActivity {
    private final ViewModelScopeBuilder mViewModelScopeBuilder = new ViewModelScopeBuilder();

    private final Set<ViewModel> mViewModels = new HashSet<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(NetworkStateManager.getInstance());
        observeStateViewCode();
    }

    //TODO tip 2: Jetpack 通过 "工厂模式" 实现 ViewModel 作用域可控，
    //目前我们在项目中提供了 Application、Activity、Fragment 三个级别的作用域，
    //值得注意的是，通过不同作用域 Provider 获得 ViewModel 实例非同一个，
    //故若 ViewModel 状态信息保留不符合预期，可从该角度出发排查 是否眼前 ViewModel 实例非目标实例所致。


    protected <T extends ViewModel> T getActivityScopeViewModel(@NonNull Class<T> modelClass) {
//        return mViewModelScope.getActivityScopeViewModel(this, modelClass);
        //TODO:可以优化先判断泛型类型
        T viewModel = mViewModelScopeBuilder.getActivityScopeViewModel(this, modelClass);
        mViewModels.add(viewModel);
        return viewModel;
    }

    protected <T extends ViewModel> T getApplicationScopeViewModel(@NonNull Class<T> modelClass) {
        return mViewModelScopeBuilder.getApplicationScopeViewModel(modelClass);
    }

    /**
     * 必须在initViewModel之后 调用
     * TODO：1、如果没有 StatusViewModel.class 类型的viewModel, 怎么办?
     * TODO：2、如果有多个StatusViewModel.class  子类怎么办？
     * 事件被覆盖的可能
     * 网络接口回调发送事件， 有可能事件的消费没有网络接口回调执行快，所以中间事件会被覆盖没有消费
     */
    private void observeStateViewCode() {
        for (ViewModel viewModel : mViewModels) {
//            System.out.println(viewModel);
            if (viewModel instanceof StatusViewModel) {
                ((StatusViewModel) viewModel).getStateCode().observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        switchStateFromEventCode(integer);
                    }
                });

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mViewModels != null && mViewModels.size() > 0) {
            mViewModels.clear();
        }
    }
}
