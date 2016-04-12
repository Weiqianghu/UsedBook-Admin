package com.weiqianghu.usedbook_admin.view.fragment;


import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.usedbook_admin.weiqianghu.usedbook_admin.R;
import com.weiqianghu.usedbook_admin.model.eneity.BookBean;
import com.weiqianghu.usedbook_admin.model.eneity.BookImgsBean;
import com.weiqianghu.usedbook_admin.model.eneity.BookModel;
import com.weiqianghu.usedbook_admin.presenter.UpdatePresenter;
import com.weiqianghu.usedbook_admin.presenter.adapter.MViewPagerAdapter;
import com.weiqianghu.usedbook_admin.util.CallBackHandler;
import com.weiqianghu.usedbook_admin.util.Constant;
import com.weiqianghu.usedbook_admin.util.FragmentUtil;
import com.weiqianghu.usedbook_admin.view.common.BaseFragment;


import java.util.ArrayList;
import java.util.List;


public class BookDetailFragment extends BaseFragment {
    public static final String TAG = BookDetailFragment.class.getSimpleName();

    private ViewPager mBookImgVp;
    private TextView mPostionTv;
    private BookModel mBookModel;

    private TextView mBookNeamTv;
    private TextView mBookIsbnTv;
    private TextView mBookAuthorTv;
    private TextView mBookPressTv;
    private TextView mBookSalesVolumeTv;
    private TextView mBookPriceTv;
    private TextView mBookCategoryTv;
    private TextView mBookPercentescribeTv;
    private TextView mBookStockTv;

    private Toolbar mToolBar;
    private DrawerLayout mDrawerLayout;


    private FragmentManager mFragmentManager;

    private View mComment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_detail;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initData();
        initView(savedInstanceState);
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mBookModel = bundle.getParcelable(Constant.BOOK);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {


        mBookImgVp = (ViewPager) mRootView.findViewById(R.id.vp_book_img);
        mPostionTv = (TextView) mRootView.findViewById(R.id.tv_position);

        mBookNeamTv = (TextView) mRootView.findViewById(R.id.tv_book_name);
        mBookIsbnTv = (TextView) mRootView.findViewById(R.id.tv_book_isbn);
        mBookAuthorTv = (TextView) mRootView.findViewById(R.id.tv_book_author);
        mBookPressTv = (TextView) mRootView.findViewById(R.id.tv_book_press);
        mBookSalesVolumeTv = (TextView) mRootView.findViewById(R.id.tv_book_sales_volume);
        mBookPriceTv = (TextView) mRootView.findViewById(R.id.tv_book_price);
        mBookCategoryTv = (TextView) mRootView.findViewById(R.id.tv_book_category);
        mBookPercentescribeTv = (TextView) mRootView.findViewById(R.id.tv_book_percent_describe);
        mBookStockTv = (TextView) mRootView.findViewById(R.id.tv_book_stock);

        Click click = new Click();
        if (mBookModel != null) {
            setBookImgs(loadBookImgs(savedInstanceState, mBookModel.getBookImgs()));
            BookBean bookBean = mBookModel.getBook();

            mBookNeamTv.setText(bookBean.getBookName());
            mBookIsbnTv.setText(bookBean.getIsbn());
            mBookAuthorTv.setText(bookBean.getAuthor());
            mBookPressTv.setText(bookBean.getPress());
            mBookSalesVolumeTv.setText(String.valueOf(bookBean.getSalesVolume()));
            mBookPriceTv.setText(String.valueOf(bookBean.getPrice()));
            mBookCategoryTv.setText(bookBean.getCategory());
            mBookPercentescribeTv.setText(bookBean.getPercentDescribe());
            mBookStockTv.setText(String.valueOf(bookBean.getStock()));
        }

        mComment = mRootView.findViewById(R.id.comment);
        mComment.setOnClickListener(click);

        mToolBar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);

        mToolBar.setNavigationIcon(R.mipmap.left);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            }
        });
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    private class Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.comment:
                    gotoCommentList();
                    break;

            }
        }
    }


    public void setBookImgs(final List<View> imgs) {
        MViewPagerAdapter adapter = new MViewPagerAdapter(imgs, mBookImgVp);
        mBookImgVp.setAdapter(adapter);
        mBookImgVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBookImgVp.setCurrentItem(position);
                mPostionTv.setText(position + 1 + "/" + imgs.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public List<View> loadBookImgs(Bundle savedInstanceState, List<BookImgsBean> imgs) {
        LayoutInflater inflater = getLayoutInflater(savedInstanceState);
        List<View> views = new ArrayList<>(3);

        if (imgs != null) {
            for (int i = 0, length = imgs.size(); i < length; i++) {
                SimpleDraweeView img = (SimpleDraweeView) inflater.inflate(R.layout.item_book_detail_img, null);
                Uri uri = Uri.parse(imgs.get(i).getImg());
                img.setImageURI(uri);
                views.add(img);
            }
        }

        for (int i = 0, length = views.size(); i < 3 - length; i++) {
            SimpleDraweeView img = (SimpleDraweeView) inflater.inflate(R.layout.item_book_detail_img, null);
            Uri uri = Uri.parse("res://com.weiqianghu.usedbook_shop/" + R.mipmap.upload_img);
            img.setImageURI(uri);
            views.add(img);
        }
        return views;
    }

    private void gotoCommentList() {
        if (mFragmentManager == null) {
            mFragmentManager = getActivity().getSupportFragmentManager();
        }
        Fragment fragment = mFragmentManager.findFragmentByTag(CommentListFragment.TAG);
        if (fragment == null) {
            fragment = new CommentListFragment();
        }

        BookBean book = mBookModel.getBook();

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.DATA, book);

        fragment.setArguments(bundle);
        Fragment from = mFragmentManager.findFragmentByTag(BookDetailFragment.TAG);
        FragmentUtil.switchContentAddToBackStack(from, fragment, R.id.main_container, mFragmentManager, CommentListFragment.TAG);
    }
}
