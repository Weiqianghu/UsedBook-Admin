package com.weiqianghu.usedbook_admin.view.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.usedbook_admin.weiqianghu.usedbook_admin.R;
import com.weiqianghu.usedbook_admin.presenter.adapter.FragmentViewPagerAdapter;
import com.weiqianghu.usedbook_admin.view.common.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StasticsFragment extends BaseFragment {

    public static final String TAG = StasticsFragment.class.getSimpleName();

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<Fragment> mViews = new ArrayList<>();
    private FragmentViewPagerAdapter mPagerAdapter;
    private FragmentManager mFragmentManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stastics;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTabLayout = (TabLayout) mRootView.findViewById(R.id.tablayout);
        mViewPager = (ViewPager) mRootView.findViewById(R.id.viewpager);

        Fragment userStasticsFragment = new UserStasticsFragment();
        Fragment shopStasticsFragment = new ShopStasticsFragment();
        Fragment orderStasticsFragment = new OrderStasticsFragment();

        mViews.add(userStasticsFragment);
        mViews.add(shopStasticsFragment);
        mViews.add(orderStasticsFragment);


        String[] mTitles = {
                getActivity().getString(R.string.user_stastics), getActivity().getString(R.string.shop_stastics)
                , getActivity().getString(R.string.order_stastics)};

        mFragmentManager = getChildFragmentManager();
        mPagerAdapter = new FragmentViewPagerAdapter(mFragmentManager, mViews, mTitles);

        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d("TabSelectedListener", "onTabUnselected");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d("TabSelectedListener", "onTabReselected");
            }
        });
    }

}
