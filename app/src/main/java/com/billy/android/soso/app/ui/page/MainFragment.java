package com.billy.android.soso.app.ui.page;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.billy.android.soso.app.BR;
import com.billy.android.soso.app.R;
import com.billy.android.soso.app.databinding.FragmentMainBinding;
import com.billy.android.soso.framwork.ui.page.DataBindingConfig;
import com.billy.android.soso.framwork.ui.page.VmDataBindingFragment;
import com.billy.android.soso.framwork.ui.viewModel.UiStateHolder;

public class MainFragment extends VmDataBindingFragment {

    private MainState mMainState ;

    @Override
    protected void initViewModel() {
        mMainState = new MainState();
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_main, BR.mainState,mMainState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentMainBinding binding = (FragmentMainBinding) getBinding();
        binding.btnJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //执行动作action
                nav().navigate(R.id.action_mainFragment_to_DetailFragment);

            }
        });
        binding.btnJumpList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                nav().navigate(R.id.action_mainFragment_to_ListGroupFragment,bundle);
            }
        });
    }

    public static class MainState extends UiStateHolder{

    }
}