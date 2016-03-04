package com.weiqianghu.usedbook_admin.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.weiqianghu.usedbook_admin.model.eneity.FailureMessageModel;


/**
 * Created by weiqianghu on 2016/2/22.
 */
public  class CallBackHandler extends Handler {
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case Constant.FAILURE:
                Bundle bundle = msg.getData();
                FailureMessageModel failureMessageModel = (FailureMessageModel) bundle.getSerializable(Constant.FAILURE_MESSAGE);
                String failureMsg = failureMessageModel.getMsg();
                handleFailureMessage(failureMsg);
                break;
        }
        handleSuccessMessage(msg);
    }

    public  void handleSuccessMessage(Message msg){}

    public void handleFailureMessage(String msg){}
}
