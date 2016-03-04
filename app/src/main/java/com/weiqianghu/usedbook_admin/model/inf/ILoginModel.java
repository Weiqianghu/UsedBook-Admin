package com.weiqianghu.usedbook_admin.model.inf;

import android.content.Context;


import com.weiqianghu.usedbook_admin.model.eneity.UserBean;

import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 胡伟强 on 2016/1/27.
 */
public interface ILoginModel {
    boolean login(Context context, LogInListener logInListener, String mobileNo, String password);

    boolean updateLogin(Context context, SaveListener saveListener, UserBean userBean);
}
