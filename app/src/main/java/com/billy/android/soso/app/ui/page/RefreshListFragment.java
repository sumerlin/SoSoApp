package com.billy.android.soso.app.ui.page;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.billy.android.soso.app.BR;
import com.billy.android.soso.app.R;
import com.billy.android.soso.app.databinding.FragmentListPageBinding;
import com.billy.android.soso.app.databinding.FragmentRefreshListBinding;
import com.billy.android.soso.app.ui.adapter.RefreshAdapter;
import com.billy.android.soso.framwork.ui.page.DataBindingConfig;
import com.billy.android.soso.framwork.ui.page.VmDataBindingFragment;
import com.billy.android.soso.framwork.ui.viewModel.UiStateHolder;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Author：summer
 * Description：
 */
public class RefreshListFragment extends VmDataBindingFragment {

    public static Fragment startFragment(String index) {
        Fragment fragment = new RefreshListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("index", index);
        fragment.setArguments(bundle);
        return fragment;
    }

    private RefreshListState mRefreshListState;
    private RefreshAdapter mRefreshAdapter;
    int refreshNum = 2;

    @Override
    protected void initViewModel() {
        mRefreshListState = new RefreshListState();
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_refresh_list, BR.refreshListState, mRefreshListState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentRefreshListBinding binding = (FragmentRefreshListBinding) getBinding();

        mRefreshAdapter = new RefreshAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(mRefreshAdapter);
//        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//
//            }
//        });


        binding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (refreshNum > 0) {
                    mRefreshAdapter.loadMore(getData(10));
                    //还有多的数据
                    refreshLayout.finishLoadMore();
                    --refreshNum;
                } else {
                    //没有更多数据（上拉加载功能将显示没有更多数据）
                    //将不会再次触发加载更多事件
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                //模拟网络加载数据
                refreshLayout.getLayout().postDelayed(() -> {
                    refreshNum = 2;
                    //网络数据高数是否还有。第二种根据内部集合判断
                    //如果刷新成功
                    mRefreshAdapter.refresh(getData(10));
                    --refreshNum;
                    //还有多的数据
                    refreshLayout.finishRefresh();
                    //刷新失败, 数据是否成功刷新 （会影响到上次更新时间的改变
//                   refreshLayout.finishRefresh(false);

                    if (refreshNum < 0) {
                        //没有更多数据（上拉加载功能将显示没有更多数据）
                        refreshLayout.finishRefreshWithNoMoreData();
                    }
//
                }, 2000);

            }
        });

        ////触发自动刷新
        binding.refreshLayout.autoRefresh();
        binding.refreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）

    }


    private final Random random = new Random();


    private Collection<String> getData(int num) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(String.valueOf(i));
        }
        return list;

    }

    private Collection<String> initData(int max) {
        int min = Math.min(10, max);
        max = Math.max(0, max);

        List<String> list = new ArrayList<>();
        if (max > min) {
//            list= Arrays.asList(new String[min + random.nextInt(max - min)]);
            for (int i = 0; i < max; i++) {
                list.add(String.valueOf(min + random.nextInt(max - min)));
            }
        } else {
            for (int i = 0; i < min; i++) {
                list.add(String.valueOf(min));
            }
//            list =  Arrays.asList(new String[min]);
        }

        return list;

    }

    public static class RefreshListState extends UiStateHolder {

    }


}
