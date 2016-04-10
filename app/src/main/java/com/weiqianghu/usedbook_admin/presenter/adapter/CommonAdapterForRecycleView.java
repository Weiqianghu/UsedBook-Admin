package com.weiqianghu.usedbook_admin.presenter.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.usedbook_admin.weiqianghu.usedbook_admin.R;
import com.weiqianghu.usedbook_admin.view.ViewHolder;
import com.weiqianghu.usedbook_admin.view.view.IRecycleViewItemClickListener;

import java.util.List;

/**
 * Created by weiqianghu on 2016/3/4.
 */
public abstract class CommonAdapterForRecycleView<T> extends RecyclerView.Adapter<ViewHolder> implements View.OnClickListener {

    protected List<T> mDatas;
    protected int mItemLayoutId;

    private int footCount = 0;

    protected static final int FOOTER_VIEW = 1;

    public CommonAdapterForRecycleView(List<T> datas, int itemLayoutId) {
        this.mDatas = datas;
        this.mItemLayoutId = itemLayoutId;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == FOOTER_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(mItemLayoutId, parent, false);
            view.setOnClickListener(this);
        }
        return ViewHolder.get(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position < mDatas.size()) {
            holder.itemView.setTag(position);
            convert(holder, mDatas.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (mDatas == null || mDatas.size() == 0) {
            return 0;
        }
        return mDatas.size() + footCount;
    }

    public void addFooter() {
        this.footCount = 1;
    }

    public void removeFooter() {
        this.footCount = 0;
    }

    public abstract void convert(ViewHolder helper, T item);

    @Override
    public int getItemViewType(int position) {
        if (position == mDatas.size()) {
            return FOOTER_VIEW;
        }
        return super.getItemViewType(position);
    }

    private IRecycleViewItemClickListener mClickListener;

    public void setOnItemClickListener(IRecycleViewItemClickListener listener) {
        this.mClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mClickListener != null) {
            mClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }

}
