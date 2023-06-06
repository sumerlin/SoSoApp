package com.billy.android.soso.framwork.ui.page;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.billy.android.soso.framwork.R;
import com.billy.android.soso.framwork.ui.base.BaseActivity;

/**
 * Author：summer
 * Description：
 * 1、DataBinding 绑定ui布局、绑定data model
 * 2、暴露DataBindingConfig 给子类具体配置
 *
 */
public abstract class DataBindingActivity extends BaseActivity {
    private ViewDataBinding mBinding;
    private TextView mTvStrictModeTip;

    protected abstract void initViewModel();

    protected abstract DataBindingConfig getDataBindingConfig();

    /**
     * TODO tip: 警惕使用。非必要情况下，尽可能不在子类中拿到 binding 实例乃至获取 view 实例。使用即埋下隐患。
     * 目前方案是于 debug 模式，对获取实例情况给予提示。
     * @return binding
     */
    protected ViewDataBinding getBinding() {
        if (isDebug() && mBinding != null) {
            if (mTvStrictModeTip == null) {
                mTvStrictModeTip = new TextView(getApplicationContext());
                mTvStrictModeTip.setAlpha(0.4f);
                mTvStrictModeTip.setPadding(
                        mTvStrictModeTip.getPaddingLeft() + 24,
                        mTvStrictModeTip.getPaddingTop() + 64,
                        mTvStrictModeTip.getPaddingRight() + 24,
                        mTvStrictModeTip.getPaddingBottom() + 24);
                mTvStrictModeTip.setGravity(Gravity.CENTER_HORIZONTAL);
                mTvStrictModeTip.setTextSize(10);
                mTvStrictModeTip.setBackgroundColor(Color.WHITE);
                @SuppressLint({"StringFormatInvalid", "LocalSuppress"})
                String tip = getString(R.string.debug_databinding_warning, getClass().getSimpleName());
                mTvStrictModeTip.setText(tip);
                ((ViewGroup) mBinding.getRoot()).addView(mTvStrictModeTip);
            }
        }
        return mBinding;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //所有viewmodel  初始化 都必须在这里
        //因为下面要做 dataBinding  要绑定 page state viewmodel, 一定是业务逻辑viewmodel， 而是view的状态表达viewmodel
        initViewModel();
        DataBindingConfig dataBindingConfig = getDataBindingConfig();

        //TODO tip: DataBinding 严格模式：
        // 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
        // 通过这方式，彻底解决 视图调用一致性问题，
        // 如此，视图调用安全性将与基于函数式编程思想 Jetpack Compose 持平。

        ViewDataBinding binding = DataBindingUtil.setContentView(this, dataBindingConfig.getLayout());
        binding.setLifecycleOwner(this);
        binding.setVariable(dataBindingConfig.getVmVariableId(), dataBindingConfig.getUiViewModel());
        SparseArray<Object> bindingParams = dataBindingConfig.getBindingParams();
        for (int i = 0, length = bindingParams.size(); i < length; i++) {
            binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i));
        }
        mBinding = binding;
    }

    public boolean isDebug() {
        return getApplicationContext().getApplicationInfo() != null &&
                (getApplicationContext().getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding.unbind();
        mBinding = null;
    }
}
