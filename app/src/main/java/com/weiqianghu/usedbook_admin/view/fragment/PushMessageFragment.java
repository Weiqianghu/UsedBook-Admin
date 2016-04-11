package com.weiqianghu.usedbook_admin.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.usedbook_admin.weiqianghu.usedbook_admin.R;
import com.weiqianghu.usedbook_admin.model.eneity.PushMessageBean;
import com.weiqianghu.usedbook_admin.presenter.SavePresenter;
import com.weiqianghu.usedbook_admin.util.CallBackHandler;
import com.weiqianghu.usedbook_admin.util.Constant;
import com.weiqianghu.usedbook_admin.view.common.BaseFragment;

import java.util.List;

import cn.bmob.v3.BmobPushManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class PushMessageFragment extends BaseFragment {

    public static final String TAG = PushMessageFragment.class.getSimpleName();

    private EditText mMessageContentEt;
    private Button mSubmitBtn;

    private Context mContext;

    private SavePresenter mSavePresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_push_message;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mContext = getActivity();

        mMessageContentEt = (EditText) mRootView.findViewById(R.id.et_message_content);
        mSubmitBtn = (Button) mRootView.findViewById(R.id.btn_submit);
        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishMessage();
            }
        });

        mSavePresenter = new SavePresenter(saveMessageHandler);
    }

    private void publishMessage() {
        String messageContent = mMessageContentEt.getText().toString().trim();
        if (TextUtils.isEmpty(messageContent)) {
            Toast.makeText(mContext, "消息内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        BmobPushManager bmobPush = new BmobPushManager(mContext);
        bmobPush.pushMessageAll(messageContent);

        PushMessageBean messageBean = new PushMessageBean();
        messageBean.setContent(messageContent);

        mSavePresenter.save(mContext, messageBean);
    }


    CallBackHandler saveMessageHandler = new CallBackHandler() {
        @Override
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    Toast.makeText(mContext, "消息推送成功", Toast.LENGTH_SHORT).show();
                    mMessageContentEt.setText("");
                    break;
            }

        }

        @Override
        public void handleFailureMessage(String msg) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    };

}
