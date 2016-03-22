package com.weiqianghu.usedbook_admin.presenter.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.usedbook_admin.weiqianghu.usedbook_admin.R;
import com.weiqianghu.usedbook_admin.model.eneity.ShopBean;
import com.weiqianghu.usedbook_admin.view.ViewHolder;
import com.weiqianghu.usedbook_admin.view.view.IRecycleViewItemClickListener;

import java.util.List;

/**
 * Created by weiqianghu on 2016/3/5.
 */
public class ShopAdapter<T> extends CommonAdapterForRecycleView implements View.OnClickListener {

    private IRecycleViewItemClickListener mClickListener;

    public void setOnItemClickListener(IRecycleViewItemClickListener listener) {
        this.mClickListener = listener;
    }

    public ShopAdapter(List datas, int itemLayoutId) {
        super(datas, itemLayoutId);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == super.FOOTER_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(mItemLayoutId, parent, false);
            view.setOnClickListener(this);
        }
        return ViewHolder.get(view);
    }

    @Override
    public void convert(ViewHolder helper, Object item) {
        ShopBean shopBean= (ShopBean) item;
        helper.setText(R.id.tv_shop_name,shopBean.getShopName());
        helper.setText(R.id.tv_contacts, shopBean.getContacts());
        helper.setText(R.id.tv_audit_state,shopBean.getVerifyStateStr());
        helper.setText(R.id.tv_date,shopBean.getUpdatedAt());
    }

    @Override
    public void onClick(View v) {
        if (mClickListener != null) {
            mClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }
}
