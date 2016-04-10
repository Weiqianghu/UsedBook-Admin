package com.weiqianghu.usedbook_admin.view.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.usedbook_admin.weiqianghu.usedbook_admin.R;
import com.weiqianghu.usedbook_admin.model.eneity.ShopBean;
import com.weiqianghu.usedbook_admin.presenter.QueryShopCountPresenter;
import com.weiqianghu.usedbook_admin.presenter.QueryShopPresenter;
import com.weiqianghu.usedbook_admin.presenter.adapter.ShopAdapter;
import com.weiqianghu.usedbook_admin.util.CallBackHandler;
import com.weiqianghu.usedbook_admin.util.Constant;
import com.weiqianghu.usedbook_admin.util.FragmentUtil;
import com.weiqianghu.usedbook_admin.view.common.BaseFragment;
import com.weiqianghu.usedbook_admin.view.customview.EmptyRecyclerView;
import com.weiqianghu.usedbook_admin.view.view.IQueryView;
import com.weiqianghu.usedbook_admin.view.view.IRecycleViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;

/**
 * A simple {@link Fragment} subclass.
 */
public class PendingAuditFragment extends BaseFragment implements IQueryView, IRecycleViewItemClickListener {
    public static String TAG = PendingAuditFragment.class.getSimpleName();

    private EmptyRecyclerView mPendingAuditRv;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private List<ShopBean> mData = new ArrayList();
    private ShopAdapter<ShopBean> mAdapter;

    private QueryShopPresenter mQueryShopPresenter;
    private QueryShopCountPresenter mQueryShopCountPresenter;
    private int count = 0;
    private static final int STEP = 15;
    private boolean isRefresh = false;

    private FragmentManager mFragmentManager;
    private Fragment mFragment;

    private int totalCount = 0;

    private Handler handler;

    public PendingAuditFragment() {
    }

    public PendingAuditFragment(Handler handler) {
        this.handler = handler;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pending_audit;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mPendingAuditRv = (EmptyRecyclerView) mRootView.findViewById(R.id.rv_pending_audit);
        View empty = mRootView.findViewById(R.id.data_empty);
        mPendingAuditRv.setEmptyView(empty);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mPendingAuditRv.setLayoutManager(mLayoutManager);
        mPendingAuditRv.setHasFixedSize(true);

        // mPendingAuditRv.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        mAdapter = new ShopAdapter<>(mData, R.layout.item_pending_audit);
        mPendingAuditRv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mPendingAuditRv.setOnScrollListener(onScrollListener);


        mQueryShopPresenter = new QueryShopPresenter(this, queryHandler);
        mQueryShopCountPresenter = new QueryShopCountPresenter(queryCountHandler);

        mSwipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.mainColor);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                count = 0;
                isRefresh = true;
                loadData(count * STEP, STEP);
                mAdapter.addFooter();
            }
        });
        initData();
    }

    private void initData() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                loadData(count * STEP, STEP);
            }
        });

    }

    private void loadData(int start, int step) {
        BmobQuery<ShopBean> query = new BmobQuery<>();
        query.addWhereEqualTo("verifyState", 0);
        mQueryShopCountPresenter.queryShopCount(getActivity(), query);
        mQueryShopPresenter.queryShop(getActivity(), start, step);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            count = 0;
            isRefresh = true;
            loadData(count * STEP, STEP);
            mAdapter.addFooter();

            Message message = new Message();
            message.what = Constant.RESET_VIEW;
            handler.sendMessage(message);
        }
    }

    private void loadMore() {
        count++;
        loadData(count * STEP, STEP);
    }

    CallBackHandler queryHandler = new CallBackHandler() {
        @Override
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    Bundle bundle = msg.getData();
                    List list = bundle.getParcelableArrayList(Constant.LIST);
                    if (list != null && list.size() > 0) {
                        if (isRefresh) {
                            mData.clear();
                            isRefresh = false;
                        }
                        mData.addAll(list);
                        mAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    } else {
                        mAdapter.notifyItemRemoved(mData.size());
                        mAdapter.removeFooter();
                    }
                    if (totalCount <= mData.size()) {
                        mAdapter.removeFooter();
                    }
                    break;
            }
        }

        @Override
        public void handleFailureMessage(String msg) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    CallBackHandler queryCountHandler = new CallBackHandler() {
        @Override
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    Bundle bundle = msg.getData();
                    if (bundle != null) {
                        totalCount = bundle.getInt(Constant.COUNT);
                    }
                    break;
            }
        }

        @Override
        public void handleFailureMessage(String msg) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    };

    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

        private int totalItemCount;
        private int lastVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (lastVisibleItem >= totalItemCount - 1 && totalCount > mData.size()) {
                mAdapter.addFooter();
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

    @Override
    public void onItemClick(View view, int position) {
        gotoFragment(position);
    }

    private void gotoFragment(int position) {
        if (mFragmentManager == null) {
            mFragmentManager = getActivity().getSupportFragmentManager();
        }
        mFragment = mFragmentManager.findFragmentByTag(ShopFragment.TAG);
        if (mFragment == null) {
            mFragment = new ShopFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.SHOP, mData.get(position));

        mFragment.setArguments(bundle);
        Fragment from = mFragmentManager.findFragmentByTag(PendingAuditFragment.TAG);
        FragmentUtil.switchContentAddToBackStack(from, mFragment, R.id.main_container, mFragmentManager, ShopFragment.TAG);

        Message message = new Message();
        message.what = Constant.SET_VIEW;
        handler.sendMessage(message);
    }

}
