package com.weiqianghu.usedbook_admin.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.usedbook_admin.weiqianghu.usedbook_admin.R;
import com.weiqianghu.usedbook_admin.model.eneity.UserBean;
import com.weiqianghu.usedbook_admin.presenter.QueryUserPresenter;
import com.weiqianghu.usedbook_admin.presenter.adapter.UserManageAdapter;
import com.weiqianghu.usedbook_admin.util.CallBackHandler;
import com.weiqianghu.usedbook_admin.util.Constant;
import com.weiqianghu.usedbook_admin.util.FragmentUtil;
import com.weiqianghu.usedbook_admin.view.common.BaseFragment;
import com.weiqianghu.usedbook_admin.view.customview.EmptyRecyclerView;
import com.weiqianghu.usedbook_admin.view.view.IRecycleViewItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class UserManageFragment extends BaseFragment implements IRecycleViewItemClickListener {
    public static final String TAG = UserManageFragment.class.getSimpleName();

    private QueryUserPresenter mQueryUserPresenter;
    private EmptyRecyclerView mRecyclerView;
    private List<UserBean> mData = new ArrayList();
    private UserManageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private boolean isRefresh = false;
    private int count = 0;
    private static final int STEP = 15;

    private Context mContext;
    private FragmentManager mFragmentManager;

    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_manage;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView(savedInstanceState);
        initData();
    }

    private void initData() {
        isRefresh = true;
        count = 0;
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                queryData(count * STEP, STEP);
            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mRecyclerView = (EmptyRecyclerView) mRootView.findViewById(R.id.recyclerview);
        View empty = mRootView.findViewById(R.id.book_empty);
        TextView mEmptyTv = (TextView) mRootView.findViewById(R.id.tv_empty);
        mEmptyTv.setText(R.string.user_manage_empty);
        mRecyclerView.setEmptyView(empty);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new UserManageAdapter(mData, R.layout.item_user_manage);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setOnScrollListener(onScrollListener);

        mSwipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.swiperefreshlayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.mainColor);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                count = 0;
                isRefresh = true;
                queryData(count * STEP, STEP);
            }
        });

        mQueryUserPresenter = new QueryUserPresenter(queryUserHandler);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        }, 500);

        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
    }

    private void queryData(int start, int step) {
        mContext = getActivity();
        mQueryUserPresenter.queryUser(mContext, Constant.ROLE_USER, start, step);
    }

    @Override
    public void onItemClick(View view, int position) {
        gotoUserDetail(position);
    }

    private void gotoUserDetail(int postion) {
        if (mFragmentManager == null) {
            mFragmentManager = getActivity().getSupportFragmentManager();
        }
        Fragment mFragment = mFragmentManager.findFragmentByTag(UserDetailFragment.TAG);
        if (mFragment == null) {
            mFragment = new UserDetailFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.DATA, mData.get(postion));

        mFragment.setArguments(bundle);
        Fragment from = mFragmentManager.findFragmentByTag(UserManageFragment.TAG);
        FragmentUtil.switchContentAddToBackStack(from, mFragment, R.id.main_container, mFragmentManager, UserDetailFragment.TAG);
    }

    CallBackHandler queryUserHandler = new CallBackHandler() {
        @Override
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    Bundle bundle = msg.getData();
                    List list = bundle.getParcelableArrayList(Constant.LIST);

                    if (isRefresh) {
                        mData.clear();
                        isRefresh = false;
                    }
                    mData.addAll(list);

                    mAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
            }
        }

        @Override
        public void handleFailureMessage(String msg) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

        private int totalItemCount;
        private int lastVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (lastVisibleItem >= totalItemCount - 1) {
                loadMore();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
            totalItemCount = mLayoutManager.getItemCount();
        }
    };

    private void loadMore() {
        count++;
        queryData(count * STEP, STEP);
    }

    @Override
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
    }
}
