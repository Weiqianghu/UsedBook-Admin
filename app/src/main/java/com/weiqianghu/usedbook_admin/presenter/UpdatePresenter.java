package com.weiqianghu.usedbook_admin.presenter;

import android.content.Context;
import android.os.Handler;


import com.weiqianghu.usedbook_admin.model.impl.UpdateModel;
import com.weiqianghu.usedbook_admin.model.inf.IUpdateModel;
import com.weiqianghu.usedbook_admin.view.view.IUpdateView;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by weiqianghu on 2016/2/24.
 */
public class UpdatePresenter<T extends BmobObject> extends CommonPresenter {
    private IUpdateModel mUpdateModel;
    private IUpdateView mUpdateView;
    private Handler handler;

    public UpdatePresenter(IUpdateView iUpdateView, Handler handler) {
        super(handler);
        this.mUpdateView = iUpdateView;
        this.handler = handler;
        mUpdateModel = new UpdateModel();
    }

    public void update(Context context, T object, String objectId) {

        UpdateListener updateListener = new UpdateListener() {
            @Override
            public void onSuccess() {
                handleSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                handleFailureMessage(i, s);
            }
        };

        mUpdateModel.update(context,object,objectId,updateListener);
    }
}
