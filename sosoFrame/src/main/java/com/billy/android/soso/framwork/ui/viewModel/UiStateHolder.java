package com.billy.android.soso.framwork.ui.viewModel;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;
import androidx.lifecycle.ViewModel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Author：summer
 * Description：
 * 1、继承ViewModel , 是更好的关联页面 感知跟 activity 或者 fragment 生命周期关系
 */
public class UiStateHolder extends ViewModel {
    public static final String VIEW = "View";
    public static final String FRAGMENT = "Fragment";
    public static final String CONTEXT = "Context";

    @StringDef({VIEW, FRAGMENT, CONTEXT}) //限定为MAN,WOMEN
    @Retention(RetentionPolicy.SOURCE) //表示注解所存活的时间,在运行时,而不会存在. class 文件.
    public @interface ContextScope { } //定义新的注释类型

    public @ContextScope String contextScope = VIEW;
}
