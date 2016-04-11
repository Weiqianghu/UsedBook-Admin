package com.weiqianghu.usedbook_admin.view.fragment;


import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.usedbook_admin.weiqianghu.usedbook_admin.R;
import com.weiqianghu.usedbook_admin.view.common.BaseFragment;


public class AboutFragment extends BaseFragment {


    public final static String TAG = AboutFragment.class.getSimpleName();

    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
    }

   /* @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initToolBar();
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    private void initToolBar() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }*/

}
