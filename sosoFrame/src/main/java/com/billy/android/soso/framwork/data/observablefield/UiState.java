package com.billy.android.soso.framwork.data.observablefield;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

/**
 * Author：summer
 * Description：
 * 1、页面的信息状态，通过State 包装来表达
 * 2、数据更新时候，自动推送到页面对应的字段
 * 3、彻底规避 Null 安全问题，构造必须填充一个默认值
 */
public class UiState<T> extends ObservableField<T> {
    private final boolean mIsDebouncing;

    /**
     * 务必根据泛型提供初值，以彻底规避 Null 安全问题
     * Be sure to provide initial values based on generics to completely avoid null security issues
     *
     * @param value initial value
     */
    public UiState(@NonNull T value) {
        this(value, false);
    }

    /**
     * 此处我们使用 "去除防抖特性" 的 ObservableField 子类 State，用以代替 MutableLiveData，
     * @param value
     * @param isDebouncing  去抖动
     */
    public UiState(@NonNull T value, boolean isDebouncing) {
        super(value);
        mIsDebouncing = isDebouncing;
    }

    @Override
    public void set(@NonNull T value) {
        boolean isUnChanged = get() == value;
        super.set(value);
        if (!mIsDebouncing && isUnChanged) {
            notifyChange();
        }
    }
}
