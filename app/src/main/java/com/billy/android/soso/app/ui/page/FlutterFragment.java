package com.billy.android.soso.app.ui.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.billy.android.soso.app.BR;
import com.billy.android.soso.app.R;
import com.billy.android.soso.app.databinding.FragmentFlutterBinding;
import com.billy.android.soso.framwork.ui.page.DataBindingConfig;
import com.billy.android.soso.framwork.ui.page.VmDataBindingFragment;
import com.billy.android.soso.framwork.ui.viewModel.UiStateHolder;

import io.flutter.embedding.android.FlutterActivity;

/**
 * Author：summer
 * Description：
 */
public class FlutterFragment extends VmDataBindingFragment {
    private FlutterState mState;

    @Override
    protected void initViewModel() {

        mState = getFragmentScopeViewModel(FlutterState.class);

    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_flutter, BR.State, mState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentFlutterBinding binding = (FragmentFlutterBinding)getBinding();

        binding.btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  //启动方式一
//                startActivity(new Intent(mActivity,FlutterActivity.class));
                //启动方式二：engine 缓存
               Intent intent =  FlutterActivity.withCachedEngine("Flutter_Engine").build(mActivity);
               startActivity(intent);

            }
        });
    }

    public static class FlutterState extends UiStateHolder {

    }
}
