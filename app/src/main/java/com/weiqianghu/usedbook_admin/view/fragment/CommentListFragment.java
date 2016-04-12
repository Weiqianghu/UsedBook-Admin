package com.weiqianghu.usedbook_admin.view.fragment;


import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.usedbook_admin.weiqianghu.usedbook_admin.R;
import com.weiqianghu.usedbook_admin.model.eneity.BookBean;
import com.weiqianghu.usedbook_admin.model.eneity.CommentBean;
import com.weiqianghu.usedbook_admin.presenter.QueryCommentPresenter;
import com.weiqianghu.usedbook_admin.presenter.adapter.CommentAdapter;
import com.weiqianghu.usedbook_admin.util.CallBackHandler;
import com.weiqianghu.usedbook_admin.util.Constant;
import com.weiqianghu.usedbook_admin.view.common.BaseFragment;
import com.weiqianghu.usedbook_admin.view.customview.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentListFragment extends BaseFragment {
    public static final String TAG = CommentListFragment.class.getSimpleName();

    private List<CommentBean> mData = new ArrayList<>();
    private CommentAdapter mCommentAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EmptyRecyclerView mRecyclerview;
    private SwipeRefreshLayout mSwiperefreshlayout;

    private QueryCommentPresenter mQueryCommentPresenter;

    private boolean isRefresh = false;
    private int count = 0;
    private static final int STEP = 15;

    private BookBean mBook = new BookBean();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comment_list;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView(savedInstanceState);
        initData();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        this.mSwiperefreshlayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.swiperefreshlayout);
        this.mRecyclerview = (EmptyRecyclerView) mRootView.findViewById(R.id.recyclerview);

        View empty = mRootView.findViewById(R.id.book_empty);
        TextView emptyTv = (TextView) mRootView.findViewById(R.id.tv_empty);
        emptyTv.setText(R.string.comment_empty);
        mRecyclerview.setEmptyView(empty);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mCommentAdapter = new CommentAdapter(mData, R.layout.item_comment, getActivity());

        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setAdapter(mCommentAdapter);
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
        mRecyclerview.setOnScrollListener(onScrollListener);

        mSwiperefreshlayout.setColorSchemeResources(R.color.mainColor);
        mSwiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                count = 0;
                isRefresh = true;
                queryData(count * STEP, STEP);
            }
        });

        mQueryCommentPresenter = new QueryCommentPresenter(queryCommentHandler);
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mBook = bundle.getParcelable(Constant.DATA);
        }

        isRefresh = true;
        count = 0;
        mSwiperefreshlayout.post(new Runnable() {
            @Override
            public void run() {
                mSwiperefreshlayout.setRefreshing(true);
                queryData(count * STEP, STEP);
            }
        });

    }

    private void queryData(int start, int step) {
        mQueryCommentPresenter.queryComment(getActivity(), start, step, mBook);
    }


    CallBackHandler queryCommentHandler = new CallBackHandler() {
        @Override
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    Bundle bundle = msg.getData();
                    if (isRefresh) {
                        mData.clear();
                        isRefresh = false;
                    }
                    List<CommentBean> list = bundle.getParcelableArrayList(Constant.LIST);
                    mData.addAll(list);
                    mCommentAdapter.notifyDataSetChanged();
                    mSwiperefreshlayout.setRefreshing(false);
            }

        }

        @Override
        public void handleFailureMessage(String msg) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            mSwiperefreshlayout.setRefreshing(false);
        }
    };

    private void loadMore() {
        count++;
        queryData(count * STEP, STEP);
    }

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

}
