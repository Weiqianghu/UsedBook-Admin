package com.weiqianghu.usedbook_admin.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.usedbook_admin.weiqianghu.usedbook_admin.R;
import com.weiqianghu.usedbook_admin.view.activity.LoginActivity;
import com.weiqianghu.usedbook_admin.view.common.BaseFragment;

import cn.bmob.v3.BmobUser;

public class SeetingsFragment extends BaseFragment {

    public static final String TAG = SeetingsFragment.class.getSimpleName();
    private Button mLogoutBtn;

    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_seetings;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Click click = new Click();


        mLogoutBtn = (Button) mRootView.findViewById(R.id.btn_logout);
        mLogoutBtn.setOnClickListener(click);

        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
    }

    private class Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_logout:
                    BmobUser.logOut(getActivity());
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    break;
            }
        }
    }

  /*  @Override
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
