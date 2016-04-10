package com.weiqianghu.usedbook_admin.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.usedbook_admin.weiqianghu.usedbook_admin.R;
import com.weiqianghu.usedbook_admin.util.Constant;
import com.weiqianghu.usedbook_admin.util.FragmentUtil;
import com.weiqianghu.usedbook_admin.view.fragment.BookManageFragment;
import com.weiqianghu.usedbook_admin.view.fragment.PendingAuditFragment;
import com.weiqianghu.usedbook_admin.view.fragment.UserManageFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;

    private FragmentManager mFragmentManager;
    private Fragment mFragment;
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
        toolbar.setTitle(R.string.pending_audit);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        initToolBar();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        gotoPendingAudit();
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

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_statistics) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void gotoBookManage() {
        toolbar.setTitle(R.string.book_manage);
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        mFragment = mFragmentManager.findFragmentByTag(BookManageFragment.TAG);
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
        mFragment = mFragmentManager.findFragmentByTag(UserManageFragment.TAG);
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
        mFragment = mFragmentManager.findFragmentByTag(PendingAuditFragment.TAG);
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
}
