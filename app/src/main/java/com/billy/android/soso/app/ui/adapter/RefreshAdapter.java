package com.billy.android.soso.app.ui.adapter;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.billy.android.soso.app.R;
import com.billy.android.soso.app.ui.page.RefreshListFragment;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Author：summer
 * Description：
 */
public class RefreshAdapter extends RecyclerView.Adapter<RefreshAdapter.RefreshViewHolder> {


    private ArrayList<String> mList;
    //    private final int mLayoutId;
    private int mLastPosition = -1;

//    public RefreshAdapter(ArrayList<String> data) {
//        this.mList = data;
//    }

    public RefreshAdapter() {
        setHasStableIds(false);
        this.mList = new ArrayList<>();
//        this.mLayoutId = layoutId;
    }

    public boolean isEmpty() {
        return mList.size() == 0;
    }


    /**
     * 和ListView一样，也是获取列表需要渲染加载的数据的数量
     */
    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    /**
     * 创建ItemView，将ItemView和Holder绑定，当然也要绑定itemView中的控件
     *
     * @param parent   就是RecyclerView对象本身
     * @param viewType 如果有多种布局，根据这个viewType的值不同，要加载不同的布局
     * @return 在onBindViewHolder方法中使用的Holder对象
     */
    @NonNull
    @Override
    public RefreshViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_refresh, parent, false);
        RefreshViewHolder holder = new RefreshViewHolder(view);
        holder.tvItem = view.findViewById(R.id.tvItem);
        holder.tvItemDesc = view.findViewById(R.id.tvItemDesc);
        return holder;
    }


    /**
     * 在这里处理数据，将position对应的JavaBean对象中的数据设置进holder.xx控件中
     *
     * @param holder   就是或新建，或复用的Holder对象
     * @param position item对应的索引
     */
    @Override
    public void onBindViewHolder(@NonNull RefreshViewHolder holder, int position) {
        String item = mList.get(position);
        holder.tvItem.setText(item);
        holder.tvItemDesc.setText(item);

    }


    public RefreshAdapter refresh(Collection<String> collection) {
        mList.clear();
        mList.addAll(collection);
        notifyDataSetChanged();
        notifyListDataSetChanged();
        mLastPosition = -1;
        return this;
    }

    public RefreshAdapter loadMore(Collection<String> collection) {
        mList.addAll(collection);
        notifyDataSetChanged();
        notifyListDataSetChanged();
        return this;
    }

    public RefreshAdapter insert(Collection<String> collection) {
        mList.addAll(0, collection);
        notifyItemRangeInserted(0, collection.size());
        notifyListDataSetChanged();
        return this;
    }

    private final DataSetObservable mDataSetObservable = new DataSetObservable();

    public void notifyListDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }

    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }


    static class RefreshViewHolder extends RecyclerView.ViewHolder {

        public TextView tvItem;
        public TextView tvItemDesc;


        public RefreshViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }
}