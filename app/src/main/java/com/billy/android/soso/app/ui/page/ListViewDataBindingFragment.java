package com.billy.android.soso.app.ui.page;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.billy.android.soso.app.BR;
import com.billy.android.soso.app.R;
import com.billy.android.soso.app.data.ListBean;
import com.billy.android.soso.app.data.ListItemBean;
import com.billy.android.soso.app.databinding.FragmentListviewDatabindingBinding;
import com.billy.android.soso.app.ui.adapter.ListViewAdapter;
import com.billy.android.soso.framwork.data.livedata.SoMutableLiveData;
import com.billy.android.soso.framwork.data.observablefield.UiState;
import com.billy.android.soso.framwork.ui.page.DataBindingConfig;
import com.billy.android.soso.framwork.ui.page.VmDataBindingFragment;
import com.billy.android.soso.framwork.ui.viewModel.UiStateHolder;

import java.util.ArrayList;
import java.util.List;

import rxhttp.wrapper.utils.LogUtil;

/**
 * Author：summer
 * Description：
 */
public class ListViewDataBindingFragment extends VmDataBindingFragment {

    private ListState mListState;
    private ListViewAdapter mListViewAdapter;
    private ListRequest mListRequest;

    @Override
    protected void initViewModel() {
        mListState = getFragmentScopeViewModel(ListState.class);
        mListRequest = getFragmentScopeViewModel(ListRequest.class);

    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_listview_databinding,
                BR.listState, mListState).addBindingParam(
                BR.adapter, mListViewAdapter = new ListViewAdapter(new ArrayList<ListItemBean>()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentListviewDatabindingBinding binding = (FragmentListviewDatabindingBinding) getBinding();
        //方式一：java代码  ， 方式二：使用data bindingAdapter  绑定data
//        List<ListItemBean> datas = new ArrayList<>();
//        datas.addAll(getData());
//        mListViewAdapte = new ListViewAdapter(datas);
//        binding.recyclerView.setAdapter(mListViewAdapte);

        binding.btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                datas.get(0).setName("xxxx");
                LogUtil.log("=========clickLogin============");
                List<ListItemBean>  datas = mListRequest.doNetData();
                mListState.listData.set(datas);

            }
        });

        binding.btnClickChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }

        });

        mListRequest.listBean.observe(this, new Observer<List<ListItemBean>>() {
            @Override
            public void onChanged(List<ListItemBean> listItemBeans) {
                mListState.listData.set(listItemBeans);
            }
        });

        binding.tvName.setText("ddd");

    }

    public List<ListItemBean> getData() {
        List<ListItemBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new ListItemBean("summer==" + i, "男"));
        }
        return list;
    }

    public List<ListItemBean> getDataChange() {
        List<ListItemBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new ListItemBean("xxxxx==" + i, "男"));
        }
        return list;
    }

    public static class ListState extends UiStateHolder {
        public UiState<String> name = new UiState<String>("");
        public UiState<List<ListItemBean>> listData = new UiState<List<ListItemBean>>(new ArrayList<ListItemBean>());

        //比如需要对某个字段 做业务逻辑的处理，之后才显示
        public String setNewName(String name) {
//            String name = null;
            return name + "11111";

        }


    }

    public static class ListRequest extends ViewModel {
        public SoMutableLiveData<List<ListItemBean>> listBean = new SoMutableLiveData<>();

        public List<ListItemBean> doNetData() {
            List<ListItemBean> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                list.add(new ListItemBean("summer==" + i, "男"));
            }
           return list;
        }
    }


}
