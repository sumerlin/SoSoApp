package com.billy.android.soso.framwork.data.livedata;

import com.kunminx.architecture.ui.callback.ProtectedUnPeekLiveData;

/**
 * Author：summer
 * Description：
 * 1、避免 LiveData 的数据出现倒灌现象
 */
public class SoLiveData<T> extends ProtectedUnPeekLiveData<T> {
}
