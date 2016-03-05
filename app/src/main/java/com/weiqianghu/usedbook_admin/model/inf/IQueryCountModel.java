package com.weiqianghu.usedbook_admin.model.inf;

import android.content.Context;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.CountListener;

/**
 * Created by weiqianghu on 2016/3/5.
 */
public interface IQueryCountModel<T extends BmobObject> {
    boolean queryCount(Context context, BmobQuery<T> query, Class<T> object, CountListener countListener);
}
