package com.billy.android.soso.framwork.ui.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

/**
 * Author：summer
 * Description：
 */
public class ApplicationInstance implements ViewModelStoreOwner {
    private final static ApplicationInstance sInstance = new ApplicationInstance();
    private ViewModelStore mAppViewModelStore;

    private ApplicationInstance() {
    }

    public static ApplicationInstance getInstance() {
        return sInstance;
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        if (mAppViewModelStore == null) mAppViewModelStore = new ViewModelStore();
        return mAppViewModelStore;
    }
}
