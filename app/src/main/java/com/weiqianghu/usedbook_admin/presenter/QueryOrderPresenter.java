package com.weiqianghu.usedbook_admin.presenter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;

import com.weiqianghu.usedbook_admin.model.eneity.OrderBean;
import com.weiqianghu.usedbook_admin.model.eneity.ShopBean;
import com.weiqianghu.usedbook_admin.model.impl.QueryModel;
import com.weiqianghu.usedbook_admin.model.inf.IQueryModel;
import com.weiqianghu.usedbook_admin.util.Constant;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by weiqianghu on 2016/4/14.
 */
public class QueryOrderPresenter extends CommonPresenter {
    private IQueryModel<OrderBean> mQueryModel;

    public QueryOrderPresenter(Handler handler) {
        super(handler);
        mQueryModel = new QueryModel<OrderBean>();
    }


    public void queryOrder(Context context) {
        FindListener<OrderBean> findListener = new FindListener<OrderBean>() {
            @Override
            public void onSuccess(List list) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Constant.LIST, (ArrayList<? extends Parcelable>) list);
                handleSuccess(bundle);
            }

            @Override
            public void onError(int i, String s) {
                handleFailureMessage(i, s);
            }
        };

        BmobQuery<OrderBean> query = new BmobQuery<>();
        query.include("address");
        mQueryModel.query(context, query, findListener);
    }
}
