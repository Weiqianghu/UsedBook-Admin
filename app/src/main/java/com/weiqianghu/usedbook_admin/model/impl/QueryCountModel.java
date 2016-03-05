package com.weiqianghu.usedbook_admin.model.impl;

import android.content.Context;

import com.weiqianghu.usedbook_admin.model.inf.IQueryCountModel;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.CountListener;

/**
 * Created by weiqianghu on 2016/3/5.
 */
public class QueryCountModel implements IQueryCountModel {

    @Override
    public boolean queryCount(Context context, BmobQuery query, Class object, CountListener countListener) {
        query.count(context,object,countListener);
        return true;
    }
}
