package com.billy.android.soso.framwork.view.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;

/**
 * Author：summer
 * Description：
 问题说明:
 当用户滑动 RecyclerView 后放开手指, RecyclerView 会继续滑动并处于 Fling 状态.
 此时用户重新触摸屏幕, RecyclerView 滑动停止, 但是无法左右滑动 ViewPager , 只能上下滑动 RecyclerView .
 问题原因:
 当用户重新触摸屏幕, 此时 RecyclerView 的 onInterceptTouchEvent() 方法还是返回了 true , 所以 ViewGroup 还是继续拦截了事件, 导致 ViewPager 无法处理.
 解决思路:
 如果是 Fling 状态的 RecyclerView , 在处理 ACTION_DOWN 事件时, 应该与 IDLE 状态下保持一致.
 Fling 状态下处理 ACTION_DOWN , onInterceptTouchEvent() 方法应该返回 false.
 */
public class XRecyclerView extends RecyclerView {

    public XRecyclerView(@NonNull Context context) {
        super(context);
    }

    public XRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        //isScrolling 为 true 表示是 Fling 状态
        boolean isScrolling = getScrollState() == SCROLL_STATE_SETTLING;
        boolean ans = super.onInterceptTouchEvent(e);
        if (ans && isScrolling && e.getAction() == MotionEvent.ACTION_DOWN) {
            //先调用 onTouchEvent() 使 RecyclerView 停下来
            onTouchEvent(e);
            //反射恢复 ScrollState
            try {
                Field field = RecyclerView.class.getDeclaredField("mScrollState");
                field.setAccessible(true);
                field.setInt(this, SCROLL_STATE_IDLE);
            } catch (NoSuchFieldException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();

            }
            return false;
        }
        return ans;

    }


}
