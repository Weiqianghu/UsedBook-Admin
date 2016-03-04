package com.usedbook_admin.weiqianghu.usedbook_admin.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.usedbook_admin.weiqianghu.usedbook_admin.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class SplashActivity extends AppCompatActivity {


    private int auditState = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Bmob.initialize(this, "0efc92162139629c26767e7eaf7a4510");


        Handler x = new Handler();
        x.postDelayed(new splashHandler(), 3000);
    }

    class splashHandler implements Runnable {

        Intent intent = null;

        public void run() {
            intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
