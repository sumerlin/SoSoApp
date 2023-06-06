package com.billy.android.soso.app.ui.page;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.billy.android.soso.app.BR;
import com.billy.android.soso.app.R;
import com.billy.android.soso.app.databinding.FragmentDetailBinding;
import com.billy.android.soso.framwork.ui.page.DataBindingConfig;
import com.billy.android.soso.framwork.ui.page.VmDataBindingFragment;
import com.billy.android.soso.framwork.ui.viewModel.UiStateHolder;

/**
 * Author：summer
 * Description：
 */
public class DetailFragment extends VmDataBindingFragment {
   private DetailState mDetailState;

    @Override
    protected void initViewModel() {

        mDetailState = new DetailState();

    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_detail, BR.detailState, mDetailState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentDetailBinding binding = (FragmentDetailBinding) getBinding();
        binding.btnJumptToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nav().navigate(R.id.action_detailFragment_to_ListGroupFragment);
            }
        });
    }

    public static class DetailState extends UiStateHolder {

    }
}
