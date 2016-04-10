package com.weiqianghu.usedbook_admin.model.impl;

import android.content.Context;
import android.util.Log;

import com.weiqianghu.usedbook_admin.model.inf.IQueryModel;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by weiqianghu on 2016/3/5.
 */
public class QueryModel<T extends BmobObject> implements IQueryModel {
    @Override
    public boolean query(Context context, BmobQuery query, FindListener findListener) {
        query.findObjects(context, findListener);
        return true;
    }

    @Override
    public boolean query(Context context, BmobQuery query, GetListener getListener, String objectId) {
        query.getObject(context, objectId, getListener);
        return true;
    }

    @Override
    public void queryCount(Context context, BmobQuery query, Class object, CountListener countListener) {
        query.count(context, object, countListener);
    }
}
