package com.weiqianghu.usedbook_admin.view.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.usedbook_admin.weiqianghu.usedbook_admin.R;
import com.weiqianghu.usedbook_admin.model.eneity.ShopBean;
import com.weiqianghu.usedbook_admin.presenter.UpdatePresenter;
import com.weiqianghu.usedbook_admin.util.CallBackHandler;
import com.weiqianghu.usedbook_admin.util.Constant;
import com.weiqianghu.usedbook_admin.view.common.BaseFragment;
import com.weiqianghu.usedbook_admin.view.view.IUpdateView;

import java.net.URI;
import java.util.List;


public class ShopFragment extends BaseFragment implements IUpdateView {

    public static String TAG = ShopFragment.class.getSimpleName();

    private TextView mContactsTv;
    private TextView mShopNameTv;
    private TextView mContactNumberTv;
    private TextView mAddressTv;
    private TextView mIdTv;

    private SimpleDraweeView mIdFrontIv;
    private SimpleDraweeView mIdBackTv;
    private ShopBean shop;

    private Spinner mSpinner;
    private ArrayAdapter<CharSequence> adapter = null;
    private RadioGroup mRadioGroup;
    private RadioButton mAuditSuccessRb;
    private RadioButton mAuditFailureRb;

    private Button mSubmitBtn;
    private ProgressBar mLoading;
    private UpdatePresenter<ShopBean> mUpdatePresenter;
    private ShopBean mShop;

    @Override
    protected int getLayoutId() {
        Fresco.initialize(getActivity());
        return R.layout.fragment_shop;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initData();
        initView(savedInstanceState);
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

        if (shop != null) {
            mContactsTv.setText(shop.getContacts());
            mContactNumberTv.setText(shop.getContactNumber());
            mShopNameTv.setText(shop.getShopName());
            String address = new StringBuffer().append(shop.getProvince()).append(shop.getCity())
                    .append(shop.getCounty()).append(shop.getDetailAddress()).toString();
            mAddressTv.setText(address);
            mIdTv.setText(shop.getIdNumber());

            Uri idFrontUri = Uri.parse(shop.getIdFrontImg());
            mIdFrontIv.setImageURI(idFrontUri);

            Uri idBackUri = Uri.parse(shop.getIdBackImg());
            mIdBackTv.setImageURI(idBackUri);
        }

        mSpinner = (Spinner) mRootView.findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.audit_failure_reason, R.layout.item_spinner);
        mSpinner.setAdapter(adapter);

        mRadioGroup = (RadioGroup) mRootView.findViewById(R.id.rg_audit_result);
        mAuditSuccessRb = (RadioButton) mRootView.findViewById(R.id.rb_audit_success);
        mAuditFailureRb = (RadioButton) mRootView.findViewById(R.id.rb_audit_failure);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == mAuditFailureRb.getId()) {
                    mSpinner.setVisibility(View.VISIBLE);
                } else {
                    mSpinner.setVisibility(View.INVISIBLE);
                }
            }
        });

        mSubmitBtn = (Button) mRootView.findViewById(R.id.btn_submit);
        mSubmitBtn.setOnClickListener(new Click());
        mLoading = (ProgressBar) mRootView.findViewById(R.id.pb_loading);

        mUpdatePresenter = new UpdatePresenter<>(this, submitHandler);
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            shop = (ShopBean) bundle.getParcelable(Constant.SHOP);
        }
    }

    private boolean beforeSubmit() {
        if (!mAuditSuccessRb.isChecked() && !mAuditFailureRb.isChecked()) {
            Toast.makeText(getActivity(), "请选择审核结果", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mAuditFailureRb.isChecked() && mSpinner.getSelectedItemId() == 0) {
            Toast.makeText(getActivity(), "请选择驳回原因", Toast.LENGTH_SHORT).show();
            return false;
        }

        mShop = new ShopBean();
        if (mAuditSuccessRb.isChecked()) {
            mShop.setVerifyState(1);
        } else if (mAuditFailureRb.isChecked() && mSpinner.getSelectedItemId() == 1) {
            mShop.setVerifyState(2);
        } else if (mAuditFailureRb.isChecked() && mSpinner.getSelectedItemId() == 2) {
            mShop.setVerifyState(3);
        }
        return true;
    }

    private class Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_submit:
                    if (beforeSubmit()) {
                        mSubmitBtn.setVisibility(View.INVISIBLE);
                        mLoading.setVisibility(View.VISIBLE);
                        mUpdatePresenter.update(getActivity(), mShop, shop.getObjectId());
                    }
                    break;
            }
        }
    }

    CallBackHandler submitHandler = new CallBackHandler() {
        @Override
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    mSubmitBtn.setVisibility(View.VISIBLE);
                    mLoading.setVisibility(View.INVISIBLE);
                    getActivity().onBackPressed();
                    break;
            }
        }

        @Override
        public void handleFailureMessage(String msg) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            mSubmitBtn.setVisibility(View.VISIBLE);
            mLoading.setVisibility(View.INVISIBLE);
        }
    };

}
