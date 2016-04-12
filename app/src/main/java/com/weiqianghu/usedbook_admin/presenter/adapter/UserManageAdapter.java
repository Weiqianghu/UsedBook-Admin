package com.weiqianghu.usedbook_admin.presenter.adapter;

import android.net.Uri;

import com.usedbook_admin.weiqianghu.usedbook_admin.R;
import com.weiqianghu.usedbook_admin.model.eneity.UserBean;
import com.weiqianghu.usedbook_admin.view.ViewHolder;

import java.util.List;

/**
 * Created by weiqianghu on 2016/4/10.
 */
public class UserManageAdapter extends CommonAdapterForRecycleView<UserBean> {
    public UserManageAdapter(List<UserBean> datas, int itemLayoutId) {
        super(datas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, UserBean item) {
        if (item.getImg() != null) {
            Uri uri = Uri.parse(item.getImg());
            helper.setImageForSimpleDraweeViewUri(R.id.iv_user_img, uri);
        } else {
            Uri uri = Uri.parse("res://com.weiqianghu.usedbook_admin/" + R.mipmap.default_user_img);
            helper.setImageForSimpleDraweeViewUri(R.id.iv_user_img, uri);
        }

        helper.setText(R.id.tv_user_mobile_no, item.getMobilePhoneNumber());
        helper.setText(R.id.tv_user_name, item.getUsername());
        if (item.getCreatedAt() != null) {
            helper.setText(R.id.tv_register_time, item.getCreatedAt().substring(0, 10));
        }
    }
}
