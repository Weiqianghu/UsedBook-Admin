package com.weiqianghu.usedbook_admin.presenter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


import com.weiqianghu.usedbook_admin.model.eneity.FailureMessageModel;
import com.weiqianghu.usedbook_admin.model.eneity.UserBean;
import com.weiqianghu.usedbook_admin.model.impl.LoginModel;
import com.weiqianghu.usedbook_admin.model.inf.ILoginModel;
import com.weiqianghu.usedbook_admin.util.Constant;
import com.weiqianghu.usedbook_admin.view.view.ILoginView;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 胡伟强 on 2016/1/27.
 */
public class LoginPresenter {
    private ILoginView mLoginView;
    private ILoginModel mLoginModel;
    private Handler handler;

    public LoginPresenter(ILoginView iLoginView, Handler handler) {
        this.mLoginView = iLoginView;
        mLoginModel = new LoginModel();
        this.handler = handler;
    }

    public void login(Context context, String mobileNo, String password) {
        LogInListener<UserBean> logInListener = new LogInListener<UserBean>() {
            @Override
            public void done(UserBean userBean, BmobException e) {
                if (userBean != null) {
                    Message message = new Message();
                    message.what = Constant.SUCCESS;
                    handler.sendMessage(message);
                } else {
                    Message message = new Message();
                    message.what = Constant.FAILURE;

                    FailureMessageModel failureMessageModel = new FailureMessageModel();
                    failureMessageModel.setMsgCode(e.getErrorCode());
                    failureMessageModel.setMsg(e.getLocalizedMessage());

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.FAILURE_MESSAGE, failureMessageModel);

                    message.setData(bundle);
                    handler.sendMessage(message);
                }
            }
        };

        mLoginModel.login(context, logInListener, mobileNo, password);
    }

    public void updateLogin(Context context,UserBean userBean){
        SaveListener saveListener=new SaveListener() {
            @Override
            public void onSuccess() {
                Message message = new Message();
                message.what = Constant.SUCCESS;
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(int i, String s) {
                Message message = new Message();
                message.what = Constant.FAILURE;

                FailureMessageModel failureMessageModel = new FailureMessageModel();
                failureMessageModel.setMsgCode(i);
                failureMessageModel.setMsg(s);

                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.FAILURE_MESSAGE, failureMessageModel);

                message.setData(bundle);
                handler.sendMessage(message);
            }
        };
        mLoginModel.updateLogin(context,saveListener,userBean);
    }
}
