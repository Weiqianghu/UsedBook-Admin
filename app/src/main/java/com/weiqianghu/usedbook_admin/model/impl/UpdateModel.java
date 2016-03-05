package com.weiqianghu.usedbook_admin.model.impl;

import android.content.Context;


import com.weiqianghu.usedbook_admin.model.inf.IUpdateModel;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by weiqianghu on 2016/2/24.
 */
public class UpdateModel implements IUpdateModel {


    @Override
    public boolean update(Context context, BmobObject object, String objectId, UpdateListener updateListener) {
        object.update(context,objectId,updateListener);
        return true;
    }
}
