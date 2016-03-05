package com.weiqianghu.usedbook_admin.presenter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.weiqianghu.usedbook_admin.model.eneity.ShopBean;
import com.weiqianghu.usedbook_admin.model.impl.QueryCountModel;
import com.weiqianghu.usedbook_admin.model.inf.IQueryCountModel;
import com.weiqianghu.usedbook_admin.util.Constant;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.CountListener;

/**
 * Created by weiqianghu on 2016/3/5.
 */
public class QueryShopCountPresenter extends CommonPresenter {
    private IQueryCountModel<ShopBean> mModel;

    public QueryShopCountPresenter(Handler handler) {
        super(handler);
        this.mModel = new QueryCountModel();
    }

    public void queryShopCount(Context context, BmobQuery<ShopBean> beanBmobQuery) {
        CountListener countListener = new CountListener() {
            @Override
            public void onSuccess(int i) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.COUNT, i);
                handleSuccess(bundle);
            }

            @Override
            public void onFailure(int i, String s) {
                handleFailureMessage(i, s);
            }
        };

        mModel.queryCount(context,beanBmobQuery,ShopBean.class,countListener);
    }
}
