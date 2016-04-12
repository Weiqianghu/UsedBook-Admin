package com.weiqianghu.usedbook_admin.presenter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;

import com.weiqianghu.usedbook_admin.model.eneity.UserBean;
import com.weiqianghu.usedbook_admin.model.impl.QueryModel;
import com.weiqianghu.usedbook_admin.model.inf.IQueryModel;
import com.weiqianghu.usedbook_admin.util.Constant;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by weiqianghu on 2016/4/10.
 */
public class QueryUserPresenter extends CommonPresenter {

    private IQueryModel<UserBean> mQueryModel;

    public QueryUserPresenter(Handler handler) {
        super(handler);
        mQueryModel = new QueryModel<UserBean>();
    }

    public void queryUser(Context context, String role, int start, int step) {
        FindListener<UserBean> findListener = new FindListener<UserBean>() {
            @Override
            public void onSuccess(List<UserBean> list) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Constant.LIST, (ArrayList<? extends Parcelable>) list);
                handleSuccess(bundle);
            }

            @Override
            public void onError(int i, String s) {
                handleFailureMessage(i, s);
            }
        };

        BmobQuery<UserBean> query = new BmobQuery<>();
        query.addWhereNotEqualTo("role", Constant.ROLE_ADMIN);
        query.include("shop");
        query.setLimit(step);
        query.setSkip(start);
        query.order("-createdAt");

        mQueryModel.query(context, query, findListener);
    }
}
