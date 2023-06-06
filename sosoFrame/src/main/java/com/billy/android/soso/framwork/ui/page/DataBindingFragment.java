package com.billy.android.soso.framwork.ui.page;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.billy.android.soso.framwork.R;
import com.billy.android.soso.framwork.ui.base.BaseFragment;

/**
 * Author：summer
 * Description：
 */
public abstract class DataBindingFragment extends BaseFragment {

    private ViewDataBinding mBinding;
    private TextView mTvStrictModeTip;

    protected abstract void initViewModel();

    protected abstract DataBindingConfig getDataBindingConfig();

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViewModel();
    }

    /**
     * TODO tip: 警惕使用。非必要情况下，尽可能不在子类中拿到 binding 实例乃至获取 view 实例。使用即埋下隐患。
     * 目前方案是于 debug 模式，对获取实例情况给予提示。
     * <p>
     *
     * @return binding
     */
    protected ViewDataBinding getBinding() {
        if (isDebug() && mBinding != null) {
            if (mTvStrictModeTip == null) {
                mTvStrictModeTip = new TextView(getContext());
                mTvStrictModeTip.setAlpha(0.5f);
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
    public View intiCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DataBindingConfig dataBindingConfig = getDataBindingConfig();
        //TODO tip: DataBinding 严格模式：
        // 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
        // 通过这方式，彻底解决 视图调用一致性问题，
        // 如此，视图调用安全性将与基于函数式编程思想 Jetpack Compose 持平。

        ViewDataBinding binding = DataBindingUtil.inflate(inflater, dataBindingConfig.getLayout(), container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setVariable(dataBindingConfig.getVmVariableId(), dataBindingConfig.getUiViewModel());
        SparseArray<Object> bindingParams = dataBindingConfig.getBindingParams();
        for (int i = 0, length = bindingParams.size(); i < length; i++) {
            binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i));
        }
        mBinding = binding;
        return mBinding.getRoot();
    }


    public boolean isDebug() {
        return mActivity.getApplicationContext().getApplicationInfo() != null &&
                (mActivity.getApplicationContext().getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding.unbind();
        mBinding = null;
    }

}
