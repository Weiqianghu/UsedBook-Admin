package com.weiqianghu.usedbook_admin.presenter.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by weiqianghu on 2016/2/25.
 */
public class FragmentViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mViews;
    private String[] mTitles;

    public FragmentViewPagerAdapter(FragmentManager fragmentManager, List<Fragment> views, String[] titles){
        super(fragmentManager);
        this.mViews=views;
        this.mTitles=titles;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }


    @Override
    public Fragment getItem(int position) {
        return mViews.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
