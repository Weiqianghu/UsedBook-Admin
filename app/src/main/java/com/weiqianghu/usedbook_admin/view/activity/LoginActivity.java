package com.weiqianghu.usedbook_admin.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.usedbook_admin.weiqianghu.usedbook_admin.R;
import com.weiqianghu.usedbook_admin.model.eneity.FailureMessageModel;
import com.weiqianghu.usedbook_admin.model.inf.ILoginModel;
import com.weiqianghu.usedbook_admin.presenter.LoginPresenter;
import com.weiqianghu.usedbook_admin.util.Constant;
import com.weiqianghu.usedbook_admin.view.customview.ClearEditText;
import com.weiqianghu.usedbook_admin.view.view.ILoginView;

import cn.bmob.v3.BmobUser;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    private Button mLoginBtn;
    private ProgressBar mLoading;
    private ClearEditText mUsernameEt;
    private ClearEditText mPasswordEt;
    private String username;
    private String password;

    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(R.string.login);

        mLoading = (ProgressBar) findViewById(R.id.pb_loading);
        mUsernameEt = (ClearEditText) findViewById(R.id.et_username);
        mPasswordEt = (ClearEditText) findViewById(R.id.et_password);
        mLoginBtn = (Button) findViewById(R.id.btn_login);
        mLoginBtn.setOnClickListener(new Click());
    }

    public Handler loginHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case Constant.FAILURE:
                    Bundle bundle = msg.getData();
                    FailureMessageModel failureMessageModel = (FailureMessageModel) bundle.getSerializable(Constant.FAILURE_MESSAGE);
                    String failureMsg = failureMessageModel.getMsg();
                    mLoading.setVisibility(View.INVISIBLE);
                    mLoginBtn.setClickable(true);
                    Toast.makeText(LoginActivity.this, failureMsg, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private class Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_login:
                    if (mLoginPresenter == null) {
                        mLoginPresenter = new LoginPresenter(LoginActivity.this, loginHandler);
                    }
                    if (beforeLogin()) {
                        mLoading.setVisibility(View.VISIBLE);
                        mLoginPresenter.login(LoginActivity.this, username, password);
                    }
                    break;
            }
        }
    }

    private boolean beforeLogin() {
        username = mUsernameEt.getText().toString().trim();
        password = mPasswordEt.getText().toString().trim();
        mLoginBtn.setClickable(false);
        if (username == null || username.length() < 1) {
            Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            mLoginBtn.setClickable(true);
            return false;
        }
        if (password == null || password.length() < 1) {
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            mLoginBtn.setClickable(true);
            return false;
        }

        if (!username .equals("admin")) {
            Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
            mLoginBtn.setClickable(true);
            return false;
        }
        return true;
    }

}
