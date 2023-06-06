package com.billy.android.soso.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.billy.android.soso.app.R;
import com.billy.android.soso.app.data.ListItemBean;
import com.billy.android.soso.app.databinding.ItemListviewBinding;

import java.util.List;

/**
 * Author：summer
 * Description：
 */
public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ListViewHolder> {
private List<ListItemBean> mDatas;

    public ListViewAdapter(List<ListItemBean> datas) {
        this.mDatas = datas;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListviewBinding binding =  DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_listview, parent, false);
//        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.item_listview, parent, false);
//        final ListViewHolder holder = new ListViewHolder( view );
        return  new ListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        ItemListviewBinding binding =  DataBindingUtil.getBinding(holder.itemView);
        //给xml中的data 赋值， 完成数据的绑定
        holder.bind(mDatas.get(position));
//        holder.getBindingAdapterPosition();

    }

    public void updateData(List<ListItemBean> list){
        mDatas.addAll(list);
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
       private final ItemListviewBinding mBinding ;

        public ListViewHolder(@NonNull ItemListviewBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(ListItemBean bean){
            //绑定实例，给xml中的data 赋值， 完成数据的绑定
            mBinding.setItemBean(bean);
            //或者这样写
            //mBinding.setVariable(BR.itemBean, bean);
            mBinding.btnClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });


        }
    }


}
