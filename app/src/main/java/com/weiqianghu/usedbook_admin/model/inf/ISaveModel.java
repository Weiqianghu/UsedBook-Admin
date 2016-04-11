package com.weiqianghu.usedbook_admin.model.inf;

import android.content.Context;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by weiqianghu on 2016/2/23.
 */
public interface ISaveModel {
    boolean save(Context context, SaveListener saveListener, BmobObject bean);
}
