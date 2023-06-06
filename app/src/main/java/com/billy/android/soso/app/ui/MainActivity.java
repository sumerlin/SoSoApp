package com.billy.android.soso.app.ui;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.billy.android.soso.app.BR;
import com.billy.android.soso.app.R;
import com.billy.android.soso.app.databinding.ActivityMainBinding;
import com.billy.android.soso.app.test.InfoList;
import com.billy.android.soso.app.ui.viewmodel.RequestViewModel;
import com.billy.android.soso.framwork.data.livedata.SoMutableLiveData;
import com.billy.android.soso.framwork.data.observablefield.UiState;
import com.billy.android.soso.framwork.ui.page.DataBindingConfig;
import com.billy.android.soso.framwork.ui.page.VmDataBindingActivity;
import com.billy.android.soso.framwork.ui.viewModel.UiStateHolder;
import com.google.android.material.textfield.TextInputEditText;

import rxhttp.wrapper.utils.LogUtil;


public class MainActivity extends VmDataBindingActivity {
//    private ActivityMainBinding binding;

    private LoginViewModel mLoginViewModel;
    private RequestViewModel mRequestViewModel;
    private ClickProxy mClickProxy;

    @Override
    protected void initViewModel() {
        mLoginViewModel = getActivityScopeViewModel(LoginViewModel.class);
        mClickProxy = getActivityScopeViewModel(ClickProxy.class);
        mRequestViewModel = getActivityScopeViewModel(RequestViewModel.class);


    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_main, BR.loginViewModel, mLoginViewModel)
                .addBindingParam(BR.clickProxy,mClickProxy);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = (ActivityMainBinding) getBinding();
        binding.btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRequestViewModel.netRequest();
            }
        });


        mClickProxy.clickPost.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                mRequestViewModel.netRequest();
            }
        });
//        mLoginViewModel.input.observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                LogUtil.log("=========onChanged============"+s);
//
//            }
//        });
        mClickProxy.clickLogin.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
//                mLoginViewModel.input.set("**"+input+"==");
//                mLoginViewModel.input.setValue("123=");
//                mLoginViewModel.input = "222";
//                mLoginViewModel.input.set("222");
//                mLoginViewModel.input.setValue("222");

                mLoginViewModel.input = "222";
                binding.setLoginViewModel(mLoginViewModel);

            }
        });

//        mLoginViewModel.input.observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                LogUtil.log("=========observe============"+s);
//            }
//        });



    }


    @Override
    public void onLoadRetry() {
        super.onLoadRetry();
        mRequestViewModel.netRequest();
    }


    public static class LoginViewModel extends UiStateHolder {
        public final UiState<String> test = new UiState<>("开始");

        //1、原类型 String， 即使是ViewModel 绑定到view也没有改变
        public String input = "111";
        //2、
//        public ObservableField<String> input = new ObservableField<>("111");
//
//        public MutableLiveData<String> input = new MutableLiveData<>("111");
        public final UiState<InfoList> infoList = new UiState<>(new InfoList());
    }


    public static class ClickProxy extends ViewModel {
        public final SoMutableLiveData<Boolean> clickLogin = new SoMutableLiveData<>();
        public final SoMutableLiveData<Boolean> clickGet = new SoMutableLiveData<>();
        public final SoMutableLiveData<Boolean> clickPost = new SoMutableLiveData<>();



        public void clickLogin(View view) {
            LogUtil.log("=========clickLogin============");
            clickLogin.setValue(Boolean.FALSE.equals(clickLogin.getValue()));
        }

        public void clickGet(View view) {
            LogUtil.log("=========clickGet============");
            clickGet.setValue(Boolean.FALSE.equals(clickGet.getValue()));
        }

        public void clickPost(View view) {
            LogUtil.log("=========clickPost=====view=======");
            clickPost.setValue(Boolean.FALSE.equals(clickPost.getValue()));
        }
    }
}