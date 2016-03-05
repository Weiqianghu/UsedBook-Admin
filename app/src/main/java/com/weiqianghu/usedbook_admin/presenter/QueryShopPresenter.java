package com.weiqianghu.usedbook_admin.presenter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;

import com.weiqianghu.usedbook_admin.model.eneity.ShopBean;
import com.weiqianghu.usedbook_admin.model.impl.QueryModel;
import com.weiqianghu.usedbook_admin.model.inf.IQueryModel;
import com.weiqianghu.usedbook_admin.util.Constant;
import com.weiqianghu.usedbook_admin.view.view.IQueryView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by weiqianghu on 2016/3/5.
 */
public class QueryShopPresenter extends CommonPresenter{
    private IQueryModel<ShopBean> mQueryModel;
    private IQueryView mQueryView;

    public QueryShopPresenter(IQueryView queryView, Handler handler){
        super(handler);
        this.mQueryView=queryView;
        mQueryModel=new QueryModel<ShopBean>();
    }

    public void queryShop(Context context,int start,int step){
        FindListener<ShopBean> findListener = new FindListener<ShopBean>() {
            @Override
            public void onSuccess(List list) {
                Bundle bundle=new Bundle();
                bundle.putParcelableArrayList(Constant.LIST, (ArrayList<? extends Parcelable>) list);
                handleSuccess(bundle);
            }

            @Override
            public void onError(int i, String s) {
                handleFailureMessage(i, s);
            }
        };

        BmobQuery<ShopBean> query = new BmobQuery<>();
        query.addWhereEqualTo("verifyState", 0);
        query.setLimit(step);
        query.setSkip(start);

        mQueryModel.query(context,query,findListener);
    }
}
