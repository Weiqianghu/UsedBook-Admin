package com.weiqianghu.usedbook_admin.view.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.usedbook_admin.weiqianghu.usedbook_admin.R;
import com.weiqianghu.usedbook_admin.model.eneity.ShopBean;
import com.weiqianghu.usedbook_admin.util.AuditUtil;
import com.weiqianghu.usedbook_admin.util.Constant;
import com.weiqianghu.usedbook_admin.util.FragmentUtil;
import com.weiqianghu.usedbook_admin.view.common.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopDetailFragment extends BaseFragment {

    public static final String TAG = ShopDetailFragment.class.getSimpleName();

    private TextView mContactsTv;
    private TextView mShopNameTv;
    private TextView mContactNumberTv;
    private TextView mAddressTv;
    private TextView mIdTv;
    private TextView mAuditStateTv;
    private SimpleDraweeView mIdFrontIv;
    private SimpleDraweeView mIdBackTv;

    private ShopBean mShop = new ShopBean();

    private Button mGotoBooksBtn;
    private FragmentManager mFragmentManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shop_detail;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView(savedInstanceState);
        initData();
        updateView();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mContactsTv = (TextView) mRootView.findViewById(R.id.tv_contacts);
        mContactNumberTv = (TextView) mRootView.findViewById(R.id.tv_contact_number);
        mShopNameTv = (TextView) mRootView.findViewById(R.id.tv_shop_name);
        mAddressTv = (TextView) mRootView.findViewById(R.id.tv_address);
        mIdTv = (TextView) mRootView.findViewById(R.id.tv_id);
        mIdFrontIv = (SimpleDraweeView) mRootView.findViewById(R.id.iv_id_front);
        mIdBackTv = (SimpleDraweeView) mRootView.findViewById(R.id.iv_id_back);
        mAuditStateTv = (TextView) mRootView.findViewById(R.id.tv_audit_state);

        mGotoBooksBtn = (Button) mRootView.findViewById(R.id.btn_goto_books);
        mGotoBooksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoBooks();
            }
        });
    }

    private void gotoBooks() {
        if (mFragmentManager == null) {
            mFragmentManager = getActivity().getSupportFragmentManager();
        }
        Fragment mFragment = mFragmentManager.findFragmentByTag(BookListOfShopFragment.TAG);
        if (mFragment == null) {
            mFragment = new BookListOfShopFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.DATA, mShop);

        mFragment.setArguments(bundle);
        Fragment from = mFragmentManager.findFragmentByTag(ShopDetailFragment.TAG);
        FragmentUtil.switchContentAddToBackStack(from, mFragment, R.id.main_container, mFragmentManager, BookListOfShopFragment.TAG);
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mShop = (ShopBean) bundle.getParcelable(Constant.SHOP);
        }
    }

    private void updateView() {
        mContactsTv.setText(mShop.getContacts());
        mContactNumberTv.setText(mShop.getContactNumber());
        mShopNameTv.setText(mShop.getShopName());
        String address = new StringBuffer().append(mShop.getProvince()).append(mShop.getCity())
                .append(mShop.getCounty()).append(mShop.getDetailAddress()).toString();
        mAddressTv.setText(address);
        mIdTv.setText(mShop.getIdNumber());

        if (mShop.getIdFrontImg() != null) {
            Uri idFrontUri = Uri.parse(mShop.getIdFrontImg());
            mIdFrontIv.setImageURI(idFrontUri);
        }
        if (mShop.getIdBackImg() != null) {
            Uri idBackUri = Uri.parse(mShop.getIdBackImg());
            mIdBackTv.setImageURI(idBackUri);
        }
        mAuditStateTv.setText(AuditUtil.getStrByFailureCode(mShop.getVerifyState()));
    }

}
