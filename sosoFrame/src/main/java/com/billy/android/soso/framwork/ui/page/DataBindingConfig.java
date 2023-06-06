package com.billy.android.soso.framwork.ui.page;

import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

/**
 * Author：summer
 * Description：
 * TODO tip: DataBindingConfig
 * 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
 * 通过这样方式，彻底解决 视图调用一致性问题，
 * 如此，视图实例安全性将与基于函数式编程思想 Jetpack Compose 持平。
 * 而 DataBindingConfig 即是在这背景下，用于为 base 页面中 DataBinding 提供绑定项。
 */
public class DataBindingConfig {
    private final int layout;

    private final int vmVariableId;

    private final ViewModel uiViewModel;

    private final SparseArray<Object> bindingParams = new SparseArray<>();

    public DataBindingConfig(@NonNull Integer layout,
                             @NonNull Integer vmVariableId,
                             @NonNull ViewModel stateViewModel) {
        this.layout = layout;
        this.vmVariableId = vmVariableId;
        this.uiViewModel = stateViewModel;
    }

    public int getLayout() {
        return layout;
    }

    public int getVmVariableId() {
        return vmVariableId;
    }

    public ViewModel getUiViewModel() {
        return uiViewModel;
    }

    public SparseArray<Object> getBindingParams() {
        return bindingParams;
    }

    public DataBindingConfig addBindingParam(@NonNull Integer variableId,
                                             @NonNull Object object) {
        if (bindingParams.get(variableId) == null) {
            bindingParams.put(variableId, object);
        }
        return this;
    }
}
