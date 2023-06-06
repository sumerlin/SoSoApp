package com.billy.android.soso.app.ui.page;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.billy.android.soso.app.BR;
import com.billy.android.soso.app.R;
import com.billy.android.soso.app.databinding.FragmentListGroupBinding;
import com.billy.android.soso.framwork.ui.page.DataBindingConfig;
import com.billy.android.soso.framwork.ui.page.VmDataBindingFragment;
import com.billy.android.soso.framwork.ui.viewModel.UiStateHolder;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * Author：summer
 * Description：
 */
public class ListGroupFragment extends VmDataBindingFragment {

    private ListGroupState mListGroupState;
    @Override
    protected void initViewModel() {
        mListGroupState = new ListGroupState();
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_list_group, BR.listGroupState, mListGroupState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentListGroupBinding binding = (FragmentListGroupBinding) getBinding();

        //实例化 适配器
        binding.viewPager2.setAdapter(new ListPagerAdapter(mActivity));
//        binding.vp.setUserInputEnabled(isChecked);  //设置是否 可以滑动
        //创建关联关系
//        new TabLayoutMediator(binding.tabLayout, binding.viewPager2, (tab, position) -> {
//            tab.setText(String.valueOf(position));
//        }).attach();
//        binding.tabLayout.setupWithViewPager();
        new TabLayoutMediator(binding.tabLayout, binding.viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(String.valueOf(position));

            }
        }).attach();
        binding.bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_item1) {
                    binding.viewPager2.setCurrentItem(0, false);
                } else if (itemId == R.id.navigation_item2) {
                    binding.viewPager2.setCurrentItem(1, false);
                } else if (itemId == R.id.navigation_item3) {
                    binding.viewPager2.setCurrentItem(2, false);
                }
                return true;
            }
        });

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager2.setCurrentItem(tab.getPosition());
                int itemId;
                int position = tab.getPosition();
                if (position == 0) itemId = R.id.navigation_item1;
                else if (position == 1) itemId = R.id.navigation_item2;
                else itemId = R.id.navigation_item3;
                binding.bottomNav.setSelectedItemId(itemId);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }





    private static class ListPagerAdapter extends FragmentStateAdapter{

        public ListPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return  ListPageFragment.startFragment(String.valueOf(position));
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

    public static class ListGroupState extends UiStateHolder{

    }
}
