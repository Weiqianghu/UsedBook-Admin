package com.weiqianghu.usedbook_admin.model.inf;

import android.content.Context;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by weiqianghu on 2016/2/24.
 */
public interface IUpdateModel<T extends BmobObject> {
    boolean update(Context context, T object, String objectId, UpdateListener updateListener);
}
