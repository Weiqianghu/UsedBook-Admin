package com.weiqianghu.usedbook_admin.presenter.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 胡伟强 on 2016/1/29.
 */
public class MViewPagerAdapter extends PagerAdapter {
    List<View> viewList;
    private ViewPager viewPager;

    public MViewPagerAdapter(List<View> views, ViewPager viewPager) {
        this.viewList = views;
        this.viewPager = viewPager;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        viewPager.removeView(viewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        viewPager.addView(viewList.get(position));
        return viewList.get(position);
    }


}
