package com.billy.android.soso.app.ui.page;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.billy.android.soso.app.BR;
import com.billy.android.soso.app.R;
import com.billy.android.soso.app.databinding.ActivityTabPagersBinding;
import com.billy.android.soso.app.databinding.FragmentDetailBinding;
import com.billy.android.soso.framwork.ui.page.DataBindingConfig;
import com.billy.android.soso.framwork.ui.page.VmDataBindingActivity;
import com.billy.android.soso.framwork.ui.viewModel.UiStateHolder;

/**
 * Author：summer
 * Description：
 */
public class TabPagersActivity  extends VmDataBindingActivity {
    private TabPagersActivity.TabPagersState mTabPagersState;

    @Override
    protected void initViewModel() {
        mTabPagersState = getActivityScopeViewModel(TabPagersActivity.TabPagersState.class);

    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_tab_pagers, BR.tabPagersState, mTabPagersState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ActivityTabPagersBinding binding = (ActivityTabPagersBinding) getBinding();
    }

    public static class TabPagersState extends UiStateHolder {

    }

}