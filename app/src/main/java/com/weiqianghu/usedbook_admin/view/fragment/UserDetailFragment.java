package com.weiqianghu.usedbook_admin.view.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.usedbook_admin.weiqianghu.usedbook_admin.R;
import com.weiqianghu.usedbook_admin.model.eneity.UserBean;
import com.weiqianghu.usedbook_admin.util.Constant;
import com.weiqianghu.usedbook_admin.util.FragmentUtil;
import com.weiqianghu.usedbook_admin.view.common.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailFragment extends BaseFragment {
    public static final String TAG = UserDetailFragment.class.getSimpleName();

    private SimpleDraweeView mUserImgIv;
    private TextView mUserNameTv;
    private TextView mMobileNoTv;
    private TextView mUserSexTv;
    private TextView mUserAgeTv;
    private TextView mUserRoleTv;
    private TextView mUserRegisterTimeTv;
    private TextView mUserUpdateTimeTv;
    private Button mGotoShopBtn;

    private UserBean mUser = new UserBean();
    private Toolbar mToolBar;
    private DrawerLayout mDrawerLayout;
    private FragmentManager mFragmentManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_detail;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView(savedInstanceState);
        initData();
        updateView();
    }

    private void updateView() {
        if (mUser.getImg() != null) {
            Uri uri = Uri.parse(mUser.getImg());
            mUserImgIv.setImageURI(uri);
        } else {
            Uri uri = Uri.parse("res://com.weiqianghu.usedbook_admin/" + R.mipmap.default_user_img);
            mUserImgIv.setImageURI(uri);
        }

        mUserNameTv.setText(mUser.getUsername());
        mMobileNoTv.setText(mUser.getMobilePhoneNumber());
        mUserSexTv.setText(mUser.getSexStr());
        mUserAgeTv.setText(String.valueOf(mUser.getAge()));

        String role = "";
        if (Constant.ROLE_USER.equals(mUser.getRole())) {
            role = "普通用户";
        } else if (Constant.ROLE_SHOP.equals(mUser.getRole())) {
            role = "商铺用户";
            mGotoShopBtn.setVisibility(View.VISIBLE);
        } else if (Constant.ROLE_ADMIN.equals(mUser.getRole())) {
            role = "管理员用户";
        }
        mUserRoleTv.setText(role);
        mUserRegisterTimeTv.setText(mUser.getCreatedAt().substring(0, 10));
        mUserUpdateTimeTv.setText(mUser.getUpdatedAt().substring(0, 10));

    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mUser = bundle.getParcelable(Constant.DATA);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mUserImgIv = (SimpleDraweeView) mRootView.findViewById(R.id.iv_user_img);
        mUserNameTv = (TextView) mRootView.findViewById(R.id.tv_username);
        mMobileNoTv = (TextView) mRootView.findViewById(R.id.tv_mobile_no);
        mUserSexTv = (TextView) mRootView.findViewById(R.id.tv_sex);
        mUserAgeTv = (TextView) mRootView.findViewById(R.id.tv_age);
        mUserRoleTv = (TextView) mRootView.findViewById(R.id.tv_role);
        mUserRegisterTimeTv = (TextView) mRootView.findViewById(R.id.tv_register_time);
        mUserUpdateTimeTv = (TextView) mRootView.findViewById(R.id.tv_update_time);
        mGotoShopBtn = (Button) mRootView.findViewById(R.id.btn_goto_shop);

        Click click = new Click();

        mGotoShopBtn.setOnClickListener(click);
        mToolBar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);

        mToolBar.setNavigationIcon(R.mipmap.left);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }


    class Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_goto_shop:
                    gotoShop();
                    break;
            }
        }
    }

    private void gotoShop() {
        if (Constant.ROLE_USER.equals(mUser.getRole()) || Constant.ROLE_ADMIN.equals(mUser.getRole())) {
            return;
        }
        if (mUser.getShop() == null) {
            return;
        }
        if (mFragmentManager == null) {
            mFragmentManager = getActivity().getSupportFragmentManager();
        }
        Fragment mFragment = mFragmentManager.findFragmentByTag(ShopDetailFragment.TAG);
        if (mFragment == null) {
            mFragment = new ShopDetailFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.SHOP, mUser.getShop());

        mFragment.setArguments(bundle);
        Fragment from = mFragmentManager.findFragmentByTag(UserDetailFragment.TAG);
        FragmentUtil.switchContentAddToBackStack(from, mFragment, R.id.main_container, mFragmentManager, ShopDetailFragment.TAG);
    }

}
