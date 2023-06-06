package com.billy.android.soso.app.ui.bind;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.billy.android.soso.app.ui.adapter.ListViewAdapter;

import java.util.List;

/**
 * Author：summer
 * Description：
 */
public class SoBindingAdapter {

//    @BindingAdapter("android:text")
//    public static void setText(TextView view, CharSequence text) {
//        //省略特殊处理...
////        String txt = text.toString().toLowerCase();
//        view.setText("aa"+text);
//    }
//
//    @BindingAdapter("textXXX")
//    public static void setTextXXX(TextView view, CharSequence text) {
//        //省略特殊处理...
////        String txt = text.toString().toLowerCase();
//        view.setText(text+"bbb");
//    }


    /**
     * 观察数据变化， 如果数据变化就执行submitList 的逻辑
     * @param recyclerView
     * @param list
     */
    @BindingAdapter(value = {"freshList"}, requireAll = false)
    public static void freshList(RecyclerView recyclerView, List list) {
        if (recyclerView.getAdapter() != null) {
            ListViewAdapter adapter = (ListViewAdapter) recyclerView.getAdapter();
//            adapter.submitList(list);
            adapter.updateData(list);
            adapter.notifyDataSetChanged();
        }
    }
}
