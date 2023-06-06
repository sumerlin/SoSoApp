package com.billy.android.soso.app.ui.page;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavAction;
import androidx.navigation.NavOptions;

import com.billy.android.soso.app.BR;
import com.billy.android.soso.app.R;
import com.billy.android.soso.app.databinding.FragmentListPageBinding;
import com.billy.android.soso.framwork.ui.page.DataBindingConfig;
import com.billy.android.soso.framwork.ui.page.VmDataBindingFragment;
import com.billy.android.soso.framwork.ui.viewModel.UiStateHolder;

/**
 * Author：summer
 * Description：
 */
public class ListPageFragment extends VmDataBindingFragment {

    public static Fragment startFragment(String index) {
        Fragment fragment = new ListPageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("index", index);
        fragment.setArguments(bundle);
        return fragment;
    }

    private ListState mListPageState;

    @Override
    protected void initViewModel() {
        mListPageState = new ListState();
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_list_page, BR.listPageState, mListPageState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String index = getArguments().getString("index");
        FragmentListPageBinding binding = (FragmentListPageBinding) getBinding();
        binding.tvName.setText("fragment" + index);
        binding.tvClass.setText("class===" + this.hashCode());

        binding.btnJumptToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                nav().navigateUp();

                nav().navigate(R.id.action_listGroupFragment_to_mainFragment);
                nav().popBackStack();

            }
        });

    }

    public static class ListState extends UiStateHolder {

    }
}
