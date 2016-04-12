package com.weiqianghu.usedbook_admin.presenter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;


import com.weiqianghu.usedbook_admin.model.eneity.BookBean;
import com.weiqianghu.usedbook_admin.model.eneity.CommentBean;
import com.weiqianghu.usedbook_admin.model.impl.QueryModel;
import com.weiqianghu.usedbook_admin.model.inf.IQueryModel;
import com.weiqianghu.usedbook_admin.util.Constant;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by weiqianghu on 2016/3/31.
 */
public class QueryCommentPresenter extends CommonPresenter {
    private IQueryModel<CommentBean> mQueryModel;

    public QueryCommentPresenter(Handler handler) {
        super(handler);
        mQueryModel = new QueryModel<CommentBean>();
    }

    public void queryComment(Context context, CommentBean commentBean) {
        BmobQuery<CommentBean> query = new BmobQuery<>();
        query.addWhereEqualTo("user", commentBean.getUser());
        query.addWhereEqualTo("order", commentBean.getOrder());
        FindListener<CommentBean> findListener = new FindListener<CommentBean>() {
            @Override
            public void onSuccess(List<CommentBean> list) {
                Bundle bundle = new Bundle();
                if (list != null && list.size() > 0) {
                    bundle.putInt(Constant.EXIST, Constant.TRUE);
                } else {
                    bundle.putInt(Constant.EXIST, Constant.FALSE);
                }

                handleSuccess(bundle);
            }

            @Override
            public void onError(int i, String s) {
                handleFailureMessage(i, s);
            }
        };

        mQueryModel.query(context, query, findListener);
    }

    public void queryComment(final Context context, int start, int step, BookBean bookBean){
        FindListener<CommentBean> findListener=new FindListener<CommentBean>() {
            @Override
            public void onSuccess(List<CommentBean> list) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Constant.LIST, (ArrayList<? extends Parcelable>) list);
                handleSuccess(bundle);
            }

            @Override
            public void onError(int i, String s) {
                handleFailureMessage(i, s);
            }
        };
        BmobQuery<CommentBean> query=new BmobQuery<>();
        query.addWhereEqualTo("book",bookBean);
        query.include("user");
        query.setLimit(step);
        query.setSkip(start);
        query.order("-updatedAt");
        mQueryModel.query(context, query, findListener);
    }

}
