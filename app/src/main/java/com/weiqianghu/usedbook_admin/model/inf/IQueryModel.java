package com.weiqianghu.usedbook_admin.model.inf;

import android.content.Context;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by weiqianghu on 2016/3/5.
 */
public interface IQueryModel<T extends BmobObject> {
    boolean query(Context context, BmobQuery<T> query, FindListener<T> findListener);
}
