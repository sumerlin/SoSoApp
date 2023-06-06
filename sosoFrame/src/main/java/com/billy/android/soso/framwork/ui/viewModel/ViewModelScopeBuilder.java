package com.billy.android.soso.framwork.ui.viewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.billy.android.soso.framwork.ui.viewModel.ApplicationInstance;

/**
 * Author：summer
 * Description：
 * 相当于ViewModel的创建工厂
 * 1、提供不同作用域的ViewModelProvider
 * 2、创建不同作用域ViewModel
 */
public class ViewModelScopeBuilder {
    private ViewModelProvider mFragmentProvider;
    private ViewModelProvider mActivityProvider;
    private ViewModelProvider mApplicationProvider;

    public <T extends ViewModel> T getFragmentScopeViewModel(@NonNull Fragment fragment, @NonNull Class<T> modelClass) {
        if (mFragmentProvider == null) mFragmentProvider = new ViewModelProvider(fragment);
        return mFragmentProvider.get(modelClass);
    }

    public <T extends ViewModel> T getActivityScopeViewModel(@NonNull AppCompatActivity activity, @NonNull Class<T> modelClass) {
        if (mActivityProvider == null) mActivityProvider = new ViewModelProvider(activity);
        return mActivityProvider.get(modelClass);
    }

    public <T extends ViewModel> T getApplicationScopeViewModel(@NonNull Class<T> modelClass) {
        if (mApplicationProvider == null)
            mApplicationProvider = new ViewModelProvider(ApplicationInstance.getInstance());
        return mApplicationProvider.get(modelClass);
    }


}
