package com.weiqianghu.usedbook_admin.model.impl;

import android.content.Context;


import com.weiqianghu.usedbook_admin.model.inf.ISaveModel;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by weiqianghu on 2016/2/23.
 */
public class SaveModel implements ISaveModel {
    @Override
    public boolean save(Context context, SaveListener saveListener, BmobObject bean) {
        bean.save(context,saveListener);
        return true;
    }
}
