package com.weiqianghu.usedbook_admin.presenter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.weiqianghu.usedbook_admin.model.eneity.FailureMessageModel;
import com.weiqianghu.usedbook_admin.util.Constant;

/**
 * Created by weiqianghu on 2016/2/24.
 */
public class CommonPresenter {
    public Handler handler;

    public CommonPresenter(){}
    public CommonPresenter(Handler handler){
        this.handler=handler;
    }

    public void handleFailureMessage(int errorCode, String error){
        Message message=new Message();
        message.what= Constant.FAILURE;

        FailureMessageModel failureMessageModel =new FailureMessageModel();
        failureMessageModel.setMsgCode(errorCode);
        failureMessageModel.setMsg(error);

        Bundle bundle=new Bundle();
        bundle.putSerializable(Constant.FAILURE_MESSAGE, failureMessageModel);

        message.setData(bundle);
        handler.sendMessage(message);
    }

    public void handleSuccess(){
        Message message=new Message();
        message.what= Constant.SUCCESS;
        handler.sendMessage(message);
    }

    public void handleSuccess(Bundle bundle){
        Message message=new Message();
        message.what= Constant.SUCCESS;
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
