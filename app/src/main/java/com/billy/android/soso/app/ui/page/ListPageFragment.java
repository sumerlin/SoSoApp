package com.billy.android.soso.app.ui.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavAction;
import androidx.navigation.NavOptions;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.billy.android.soso.app.BR;
import com.billy.android.soso.app.R;
import com.billy.android.soso.app.data.ListItemBean;
import com.billy.android.soso.app.databinding.FragmentListPageBinding;
import com.billy.android.soso.app.databinding.ItemListviewBinding;
import com.billy.android.soso.framwork.ui.page.DataBindingConfig;
import com.billy.android.soso.framwork.ui.page.VmDataBindingFragment;
import com.billy.android.soso.framwork.ui.viewModel.UiStateHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：summer
 * Description：
 */
public class ListPageFragment extends VmDataBindingFragment {

    public static Fragment startFragment(String index) {
        Fragment fragment = new ListPageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("index", index);
        fragment.setArguments(bundle);
        return fragment;
    }

    private ListState mListPageState;

    @Override
    protected void initViewModel() {
        mListPageState = new ListState();
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_list_page, BR.listPageState, mListPageState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String index = getArguments().getString("index");
        FragmentListPageBinding binding = (FragmentListPageBinding) getBinding();
        binding.tvName.setText("fragment" + index);
        binding.tvClass.setText("class===" + this.hashCode());

        binding.btnJumptToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                nav().navigateUp();

                nav().navigate(R.id.action_listGroupFragment_to_mainFragment);
                nav().popBackStack();

            }
        });

        ListAdapter mAdapter = new ListAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        binding.recyclerView.setAdapter(mAdapter);

    }

    private static class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
        private List<ListItemBean> mDatas = getData();

        private List<ListItemBean> getData() {
            List<ListItemBean> list = new ArrayList<>();
            for (int i = 0; i < 80; i++) {
                list.add(new ListItemBean("summer==" + i, "男"));
            }
            return list;
        }

        public ListAdapter() {

        }


        @NonNull
        @Override
        public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemListviewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_listview, parent, false);
            return new ListViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
            ItemListviewBinding binding = DataBindingUtil.getBinding(holder.itemView);
            //给xml中的data 赋值， 完成数据的绑定
            holder.bind(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }


        public static class ListViewHolder extends RecyclerView.ViewHolder {

            private final ItemListviewBinding mBinding;

            public ListViewHolder(@NonNull ItemListviewBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void bind(ListItemBean bean) {
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

    public static class ListState extends UiStateHolder {

    }
}
