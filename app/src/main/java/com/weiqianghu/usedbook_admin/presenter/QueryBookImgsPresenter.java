package com.weiqianghu.usedbook_admin.presenter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;


import com.weiqianghu.usedbook_admin.model.eneity.BookBean;
import com.weiqianghu.usedbook_admin.model.eneity.BookImgsBean;
import com.weiqianghu.usedbook_admin.model.eneity.OrderBean;
import com.weiqianghu.usedbook_admin.model.impl.QueryModel;
import com.weiqianghu.usedbook_admin.model.inf.IQueryModel;
import com.weiqianghu.usedbook_admin.util.Constant;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by weiqianghu on 2016/3/12.
 */
public class QueryBookImgsPresenter extends CommonPresenter {
    private IQueryModel<BookImgsBean> mQueryModel;

    public QueryBookImgsPresenter(Handler handler) {
        super(handler);
        mQueryModel = new QueryModel<BookImgsBean>();
    }

    public void queryBookImgs(Context context, final BookBean bookBean) {
        FindListener<BookImgsBean> findListener = new FindListener<BookImgsBean>() {
            @Override
            public void onSuccess(List list) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Constant.LIST, (ArrayList<? extends Parcelable>) list);
                bundle.putParcelable(Constant.BOOK, bookBean);
                handleSuccess(bundle);
            }

            @Override
            public void onError(int i, String s) {
                handleFailureMessage(i, s);
            }
        };

        BmobQuery<BookImgsBean> query = new BmobQuery<>();
        query.addWhereEqualTo("book", bookBean);
        mQueryModel.query(context, query, findListener);
    }

    public void queryBookImgs(Context context, final OrderBean orderBean) {
        FindListener<BookImgsBean> findListener = new FindListener<BookImgsBean>() {
            @Override
            public void onSuccess(List list) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Constant.LIST, (ArrayList<? extends Parcelable>) list);
                bundle.putParcelable(Constant.BOOK, orderBean);
                handleSuccess(bundle);
            }

            @Override
            public void onError(int i, String s) {
                handleFailureMessage(i, s);
            }
        };

        BmobQuery<BookImgsBean> query = new BmobQuery<>();
        query.addWhereEqualTo("book", orderBean.getBook());
        mQueryModel.query(context, query, findListener);
    }

}
