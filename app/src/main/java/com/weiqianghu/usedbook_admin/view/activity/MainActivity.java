package com.weiqianghu.usedbook_admin.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.usedbook_admin.weiqianghu.usedbook_admin.R;
import com.weiqianghu.usedbook_admin.util.Constant;
import com.weiqianghu.usedbook_admin.util.FragmentUtil;
import com.weiqianghu.usedbook_admin.view.fragment.AboutFragment;
import com.weiqianghu.usedbook_admin.view.fragment.BookManageFragment;
import com.weiqianghu.usedbook_admin.view.fragment.PendingAuditFragment;
import com.weiqianghu.usedbook_admin.view.fragment.PushMessageFragment;
import com.weiqianghu.usedbook_admin.view.fragment.SeetingsFragment;
import com.weiqianghu.usedbook_admin.view.fragment.StasticsFragment;
import com.weiqianghu.usedbook_admin.view.fragment.UserManageFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;

    private FragmentManager mFragmentManager;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "邮件联系我：weiqianghu_ecus@163.com", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        initToolBar();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        gotoBookManage();
    }

    private void initToolBar() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            gotoSettingsForTop();
            return true;
        }
        if (id == R.id.action_about) {
            gotoAboutForTop();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_pending_audit) {
            gotoPendingAudit();
        } else if (id == R.id.nav_user_manage) {
            gotoUserManage();
        } else if (id == R.id.nav_book_manage) {
            gotoBookManage();
        } else if (id == R.id.nav_settings) {
            toolbar.setTitle(R.string.action_settings);
            gotoSettingsForNav();
        } else if (id == R.id.nav_about) {
            toolbar.setTitle(R.string.action_about);
            gotoAboutForNav();
        } else if (id == R.id.nav_statistics) {
            gotoStastics();
        } else if (id == R.id.nav_publish_notice) {
            gotoPushNotice();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void gotoStastics() {
        toolbar.setTitle(R.string.stastics);
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        Fragment mFragment = mFragmentManager.findFragmentByTag(StasticsFragment.TAG);
        if (mFragment == null) {
            mFragment = new StasticsFragment();
        }
        FragmentUtil.addContentNoAnimation(R.id.main_container, mFragment, mFragmentManager, StasticsFragment.TAG);
    }

    private void gotoPushNotice() {
        toolbar.setTitle(R.string.publish_notice);
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        Fragment mFragment = mFragmentManager.findFragmentByTag(PushMessageFragment.TAG);
        if (mFragment == null) {
            mFragment = new PushMessageFragment();
        }
        FragmentUtil.addContentNoAnimation(R.id.main_container, mFragment, mFragmentManager, PushMessageFragment.TAG);
    }

    private void gotoBookManage() {
        toolbar.setTitle(R.string.book_manage);
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        Fragment mFragment = mFragmentManager.findFragmentByTag(BookManageFragment.TAG);
        if (mFragment == null) {
            mFragment = new BookManageFragment();
        }
        FragmentUtil.addContentNoAnimation(R.id.main_container, mFragment, mFragmentManager, BookManageFragment.TAG);
    }

    private void gotoUserManage() {
        toolbar.setTitle(R.string.user_manage);
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        Fragment mFragment = mFragmentManager.findFragmentByTag(UserManageFragment.TAG);
        if (mFragment == null) {
            mFragment = new UserManageFragment();
        }
        FragmentUtil.addContentNoAnimation(R.id.main_container, mFragment, mFragmentManager, UserManageFragment.TAG);
    }

    private void gotoPendingAudit() {
        toolbar.setTitle(R.string.pending_audit);
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        Fragment mFragment = mFragmentManager.findFragmentByTag(PendingAuditFragment.TAG);
        if (mFragment == null) {
            mFragment = new PendingAuditFragment(toolBarHandler);
        }
        FragmentUtil.addContentNoAnimation(R.id.main_container, mFragment, mFragmentManager, PendingAuditFragment.TAG);
    }

    private Handler toolBarHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case Constant.SET_VIEW:
                    toolbar.setNavigationIcon(R.mipmap.left);
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    break;
                case Constant.RESET_VIEW:
                    initToolBar();
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    break;
            }
        }
    };

    private void gotoSettingsForNav() {
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        Fragment mFragment = mFragmentManager.findFragmentByTag(SeetingsFragment.TAG);
        if (mFragment == null) {
            mFragment = new SeetingsFragment();
        }
        FragmentUtil.addContentNoAnimation(R.id.main_container, mFragment, mFragmentManager, SeetingsFragment.TAG);
    }


    private void gotoAboutForNav() {
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        Fragment mFragment = mFragmentManager.findFragmentByTag(AboutFragment.TAG);
        if (mFragment == null) {
            mFragment = new AboutFragment();
        }
        FragmentUtil.addContentNoAnimation(R.id.main_container, mFragment, mFragmentManager, AboutFragment.TAG);
    }

    private void gotoAboutForTop() {
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        Fragment mFragment = mFragmentManager.findFragmentByTag(AboutFragment.TAG);
        if (mFragment == null) {
            mFragment = new AboutFragment();
        }

        List<Fragment> fragments = mFragmentManager.getFragments();
        Fragment from = new Fragment();
        for (int i = fragments.size() - 1; i >= 0; i--) {
            if (null != fragments.get(i) && fragments.get(i).isResumed()) {
                from = fragments.get(i);
                break;
            }
        }
        if (from == mFragment) {
            return;
        }
        if (fragments.size() <= 1) {
            return;
        }

        FragmentUtil.switchContentAddToBackStack(from, mFragment, R.id.main_container, mFragmentManager, AboutFragment.TAG);
    }

    private void gotoSettingsForTop() {
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        Fragment mFragment = mFragmentManager.findFragmentByTag(SeetingsFragment.TAG);
        if (mFragment == null) {
            mFragment = new SeetingsFragment();
        }

        List<Fragment> fragments = mFragmentManager.getFragments();
        Fragment from = new Fragment();
        for (int i = fragments.size() - 1; i >= 0; i--) {
            if (null != fragments.get(i) && fragments.get(i).isResumed()) {
                from = fragments.get(i);
                break;
            }
        }
        if (from == mFragment) {
            return;
        }

        if (fragments.size() <= 1) {
            return;
        }

        FragmentUtil.switchContentAddToBackStack(from, mFragment, R.id.main_container, mFragmentManager, SeetingsFragment.TAG);
    }

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private int count = 0;
    public void exit() {
        if (count < 1) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
        }
        if (count < 2) {
            count++;
            new Thread() {
                public void run() {
                    try {
                        sleep(3000);
                        count = 0;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } else {
            finish();
        }
    }*/
}
