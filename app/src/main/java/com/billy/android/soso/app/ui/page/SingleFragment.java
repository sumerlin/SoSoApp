package com.billy.android.soso.app.ui.page;

import com.billy.android.soso.app.BR;
import com.billy.android.soso.app.R;
import com.billy.android.soso.framwork.ui.page.DataBindingConfig;
import com.billy.android.soso.framwork.ui.page.VmDataBindingFragment;
import com.billy.android.soso.framwork.ui.viewModel.UiStateHolder;

/**
 * Author：summer
 * Description：
 */
public class SingleFragment extends VmDataBindingFragment {
    private SingleState mState;

    @Override
    protected void initViewModel() {

        mState = getFragmentScopeViewModel(SingleState.class);

    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_single, BR.State, mState);
    }


    public static class SingleState extends UiStateHolder {

    }
}
