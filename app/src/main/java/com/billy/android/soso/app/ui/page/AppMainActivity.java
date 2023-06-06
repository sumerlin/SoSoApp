package com.billy.android.soso.app.ui.page;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import android.os.Bundle;

import com.billy.android.soso.app.BR;
import com.billy.android.soso.app.R;
import com.billy.android.soso.framwork.ui.page.DataBindingConfig;
import com.billy.android.soso.framwork.ui.page.VmDataBindingActivity;
import com.billy.android.soso.framwork.ui.viewModel.UiStateHolder;

public class AppMainActivity extends VmDataBindingActivity {
    private  AppMainState mAppMainState;

    @Override
    protected void initViewModel() {
        mAppMainState = getActivityScopeViewModel(AppMainState.class);

    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_app_main, BR.appMain, mAppMainState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static class AppMainState extends UiStateHolder {

    }
}