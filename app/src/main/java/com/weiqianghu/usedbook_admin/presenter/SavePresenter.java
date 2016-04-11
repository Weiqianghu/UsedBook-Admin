package com.weiqianghu.usedbook_admin.presenter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.weiqianghu.usedbook_admin.model.eneity.FailureMessageModel;
import com.weiqianghu.usedbook_admin.model.impl.SaveModel;
import com.weiqianghu.usedbook_admin.model.inf.ISaveModel;
import com.weiqianghu.usedbook_admin.util.Constant;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by weiqianghu on 2016/2/23.
 */
public class SavePresenter extends CommonPresenter {
    private ISaveModel mSaveModel;

    public SavePresenter(Handler handler) {
        super(handler);
        mSaveModel = new SaveModel();
    }

    public void save(Context context, BmobObject bean) {
        SaveListener saveListener = new SaveListener() {
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

        mSaveModel.save(context, saveListener, bean);
    }
}
