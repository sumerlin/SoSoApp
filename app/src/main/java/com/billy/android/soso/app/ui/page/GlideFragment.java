package com.billy.android.soso.app.ui.page;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.billy.android.soso.app.BR;
import com.billy.android.soso.app.R;
import com.billy.android.soso.app.databinding.FragmentGlideBinding;
import com.billy.android.soso.framwork.common.image.ImageLoader;
import com.billy.android.soso.framwork.ui.page.DataBindingConfig;
import com.billy.android.soso.framwork.ui.page.VmDataBindingFragment;
import com.billy.android.soso.framwork.ui.viewModel.UiStateHolder;

/**
 * Author：summer
 * Description：
 */
public class GlideFragment extends VmDataBindingFragment {
    private GlideState mState;

    @Override
    protected void initViewModel() {

        mState = getFragmentScopeViewModel(GlideState.class);

    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_glide, BR.State, mState);
    }

    private FragmentGlideBinding binding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = (FragmentGlideBinding) getBinding();


//        binding.btnClick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showImage();
//
//            }
//        });

        showImage();

    }


    private void showImage() {

        String url = "https://photocdn.sohu.com/20110507/Img280510933.jpg";

        String imgUrl2 = "https://p4.itc.cn/images01/20230604/7c9b22395ec14eaea8613a1f6a194046.jpeg";


        ImageLoader.with(mActivity)
                .load(R.drawable.check)
                .into(binding.img04)
                .show();
        ImageLoader
                .with(mActivity)
                .load("")
                .into(binding.img05)
                .showBlur(25);
        ImageLoader
                .with(mActivity)
                .load(url)
                .into(binding.img06)
                .showRadius(25);
        ImageLoader
                .with(mActivity)
                .load(url)
                .into(binding.img07)
                .showCircle();





    }


    public static class GlideState extends UiStateHolder {

    }
}
