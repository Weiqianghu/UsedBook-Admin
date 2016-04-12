package com.weiqianghu.usedbook_admin.view.fragment;


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
import com.weiqianghu.usedbook_admin.model.eneity.BookBean;
import com.weiqianghu.usedbook_admin.model.eneity.BookModel;
import com.weiqianghu.usedbook_admin.model.eneity.ShopBean;
import com.weiqianghu.usedbook_admin.presenter.QueryBookImgsPresenter;
import com.weiqianghu.usedbook_admin.presenter.QueryBooksPresenter;
import com.weiqianghu.usedbook_admin.presenter.adapter.BookAdapter;
import com.weiqianghu.usedbook_admin.util.CallBackHandler;
import com.weiqianghu.usedbook_admin.util.Constant;
import com.weiqianghu.usedbook_admin.util.FragmentUtil;
import com.weiqianghu.usedbook_admin.view.common.BaseFragment;
import com.weiqianghu.usedbook_admin.view.customview.EmptyRecyclerView;
import com.weiqianghu.usedbook_admin.view.view.IRecycleViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookListOfShopFragment extends BaseFragment implements IRecycleViewItemClickListener {

    public static final String TAG = BookListOfShopFragment.class.getSimpleName();
    private FragmentManager mFragmentManager;
    private Fragment mFragment;

    private EmptyRecyclerView mRecyclerView;
    private List<BookModel> mData = new ArrayList();
    private List<BookBean> mBooks = new ArrayList<>();
    private BookAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private QueryBooksPresenter mQueryBooksPresenter;
    private QueryBookImgsPresenter mQueryBookImgsPresenter;
    private boolean isRefresh = false;
    private int count = 0;
    private static final int STEP = 15;

    private ShopBean mShop = null;

    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_manage;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        mRecyclerView = (EmptyRecyclerView) mRootView.findViewById(R.id.recyclerview);
        View empty = mRootView.findViewById(R.id.book_empty);
        TextView mEmptyTv = (TextView) mRootView.findViewById(R.id.tv_empty);
        mEmptyTv.setText(R.string.book_empty);
        mRecyclerView.setEmptyView(empty);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new BookAdapter(mData, R.layout.item_book);

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

        mQueryBooksPresenter = new QueryBooksPresenter(queryBooksHandler);
        mQueryBookImgsPresenter = new QueryBookImgsPresenter(queryBookImgsHandler);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        }, 500);
        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mShop = bundle.getParcelable(Constant.DATA);
        }
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

    private void queryData(int start, int step) {
        mQueryBooksPresenter.queryBooks(getActivity(), start, step, mShop);
    }

    CallBackHandler queryBooksHandler = new CallBackHandler() {
        @Override
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    Bundle bundle = msg.getData();
                    List list = bundle.getParcelableArrayList(Constant.LIST);
                    if (list != null && list.size() > 0) {
                        mBooks.clear();
                        mBooks.addAll(list);
                        for (int i = 0, length = mBooks.size(); i < length; i++) {
                            mQueryBookImgsPresenter.queryBookImgs(getActivity(), (BookBean) list.get(i));
                        }
                    } else {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
            }
        }

        @Override
        public void handleFailureMessage(String msg) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    CallBackHandler queryBookImgsHandler = new CallBackHandler() {
        @Override
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    Bundle bundle = msg.getData();
                    List list = bundle.getParcelableArrayList(Constant.LIST);
                    BookBean bookBean = bundle.getParcelable(Constant.BOOK);

                    BookModel bookModel = new BookModel();
                    bookModel.setBook(bookBean);
                    bookModel.setBookImgs(list);

                    if (isRefresh) {
                        mData.clear();
                        isRefresh = false;
                    }
                    mData.add(bookModel);

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

    @Override
    public void onItemClick(View view, int position) {
        gotoBookDetail(position);
    }

    private void gotoBookDetail(int position) {
        if (mFragmentManager == null) {
            mFragmentManager = getActivity().getSupportFragmentManager();
        }
        mFragment = mFragmentManager.findFragmentByTag(BookDetailFragment.TAG);
        if (mFragment == null) {
            mFragment = new BookDetailFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.BOOK, mData.get(position));

        mFragment.setArguments(bundle);
        Fragment from = mFragmentManager.findFragmentByTag(BookListOfShopFragment.TAG);
        FragmentUtil.switchContentAddToBackStack(from, mFragment, R.id.main_container, mFragmentManager, BookDetailFragment.TAG);
    }

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
